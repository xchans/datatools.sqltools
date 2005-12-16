/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.datatools.sqltools.sqleditor.internal.sql;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

/**
 * This class implements a content assist processor for SQL code.
 * <p>
 * This processor should be registered with a content assistant in order to get
 * involved in the assisting process.
 * 
 * @author Hetty Dougherty
 *  
 */
public class SQLCompletionProcessor implements IContentAssistProcessor {

    private char[] fProposalAutoActivationSet;

    /**
     * Simple content assist tip closer. The tip is valid in a range of 5
     * characters around its popup location.
     */
    protected static class Validator implements IContextInformationValidator, IContextInformationPresenter {
        protected int fInstallOffset;

        /**
         * @see IContextInformationValidator#isContextInformationValid(int)
         */
        public boolean isContextInformationValid( int offset ) {
            return Math.abs( fInstallOffset - offset ) < 5;
        }

        /**
         * @see IContextInformationValidator#install(IContextInformation,
         *      ITextViewer, int)
         */
        public void install( IContextInformation info, ITextViewer viewer, int offset ) {
            fInstallOffset = offset;
        }

        public boolean updatePresentation( int position, TextPresentation presentation ) {
            return true;
        }
    };

    private static class CompletionProposalComparator implements Comparator {
        public int compare( Object o1, Object o2 ) {
            ICompletionProposal c1 = (ICompletionProposal) o1;
            ICompletionProposal c2 = (ICompletionProposal) o2;
            return c1.getDisplayString().compareTo( c2.getDisplayString() );
        }
    };

    protected IContextInformationValidator fValidator = new Validator();

    private ISQLCompletionEngine fCompletionEngine;
    private Comparator fComparator;
    private ISQLDBProposalsService fDBProposalsService;

    public SQLCompletionProcessor() {
        super();

        // activation/trigger to invoke content assist
        char[] completionChars = { '.' };
        setCompletionProposalAutoActivationCharacters( completionChars );

        fCompletionEngine = new SQLCompletionEngine();
        fComparator = new CompletionProposalComparator();
        fDBProposalsService = null;
    }

    /**
     * Tells this processor to order the proposals alphabetically.
     * 
     * @param order <code>true</code> if proposals should be ordered.
     */
    public void orderProposalsAlphabetically( boolean order ) {
        fComparator = order ? new CompletionProposalComparator() : null;
    }

    /**
     * Returns a list of proposed content completions based on the specified
     * location within the document that corresponds to the current cursor
     * position within the text-editor control.
     * 
     * @param viewer the viewer whose document is used to compute the proposals
     * @param documentPosition a location within the document
     * @return an array of content-assist proposals
     */
    public ICompletionProposal[] computeCompletionProposals( ITextViewer viewer, int documentOffset ) {
        ICompletionProposal[] result = null;
        
        try {
            IDocument doc = viewer.getDocument();
            ITypedRegion partition = null;

            if (documentOffset > 0) {
                if (doc.getChar( documentOffset - 1 ) == ';')
                    partition = viewer.getDocument().getPartition( documentOffset );
                else
                    // for incomplete statement.
                    partition = viewer.getDocument().getPartition( documentOffset - 1 );
            }
            else
                partition = viewer.getDocument().getPartition( documentOffset );

            result = fCompletionEngine.computeProposals( doc, partition, documentOffset );
        }
        catch (BadLocationException x) {
        }
        ;
        
        if (result != null)
            result = order( result );        
        
        return result;
    }

    /**
     * Returns a list of content-assist tips based on the specified location
     * within the document that corresponds to the current cursor position
     * within the text-editor control.
     * 
     * @param viewer the viewer whose document is used to compute the tips
     * @param documentPosition a location within the document
     * @return an array of content-assist tips
     */

    public IContextInformation[] computeContextInformation( ITextViewer viewer, int documentOffset ) {
        IContextInformation[] result = null;
        return result;
    }

    /**
     * Returns a string of characters which when pressed should automatically
     * display content-assist proposals.
     * 
     * @see IContentAssistProcessor.getCompletionProposalAutoActivationCharacters()
     * 
     * @return string of characters
     */
    public char[] getCompletionProposalAutoActivationCharacters() {
        return fProposalAutoActivationSet;
    }

    /**
     * Returns a string of characters which when pressed should automatically
     * display a content-assist tip.
     * 
     * @return string of characters
     */
    public char[] getContextInformationAutoActivationCharacters() {
        return new char[] { '#' };
    }

    /**
     * Returns a delegate used to determine when a displayed tip should be
     * dismissed.
     * 
     * @return a tip closer
     */
    public IContextInformationValidator getContextInformationValidator() {
        return fValidator;
    }

    /**
     * Gets the DBProposalsService.
     * 
     * @return the DBProposalsService
     */
    public ISQLDBProposalsService getDBProposalService() {
        return fDBProposalsService;
    }

    /**
     * Returns the reason why the content-assist processor was unable to produce
     * any proposals or tips.
     * 
     * @return an error message or null if no error occurred
     */
    public String getErrorMessage() {
        return null;
    }

    /**
     * Sets the <code>ISQLDBProposalsService</code> to use to generate DB object
     * proposals (tables, columns) for content assist.
     * 
     * @param dbProposalsService the <code>ISQLDBProposalsService</code> object to use
     */
    public void setDBProposalsService( ISQLDBProposalsService dbProposalsService ) {
        fDBProposalsService = dbProposalsService;
        ((SQLCompletionEngine) fCompletionEngine).setDBProposalsService( fDBProposalsService );
    }

    /**
     * Orders the given proposals.
     * 
     * @params ICompletionProposal[] List of proposals to be ordered
     */
    private ICompletionProposal[] order( ICompletionProposal[] proposals ) {
        if (fComparator != null)
            Arrays.sort( proposals, fComparator );
        return proposals;
    }

    /**
     * Sets this processor's set of characters triggering the activation of the
     * completion proposal computation.
     * 
     * @param activationSet the activation set
     */
    public void setCompletionProposalAutoActivationCharacters( char[] activationSet ) {
        fProposalAutoActivationSet = activationSet;
    }

}