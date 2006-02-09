/**
 * Created on 2004-10-25
 * 
 * Copyright (c) Sybase, Inc. 2004-2006 All rights reserved.
 */
package org.eclipse.datatools.sqltools.sqleditor.internal.actions;

import org.eclipse.datatools.sqltools.sql.parser.ast.Node;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IAbstractTextEditorHelpContextIds;

/**
 * @author Hui Cao
 *  
 */
class CopyToClipboardAction extends SelectionDispatchAction
{

    private final static int        _MAX_REPEAT_COUNT = 10;

    private final Clipboard         _fClipboard;
    private SelectionDispatchAction _fPasteAction;          //may be null

    public CopyToClipboardAction(IWorkbenchSite site, Clipboard clipboard, SelectionDispatchAction pasteAction)
    {
        super(site);

        setText(Messages.getString("CopyAction.label")); //$NON-NLS-1$
        setToolTipText(Messages.getString("CopyAction.tooltip")); //$NON-NLS-1$

        _fClipboard = clipboard;
        _fPasteAction = pasteAction;

        ISharedImages workbenchImages = PlatformUI.getWorkbench().getSharedImages();
        setDisabledImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
        setImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IAbstractTextEditorHelpContextIds.COPY_ACTION);

        update(getSelection());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jdt.ui.actions.SelectionDispatchAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void selectionChanged(IStructuredSelection selection)
    {
        setEnabled(canEnable(selection));
    }

    private boolean canEnable(IStructuredSelection selection)
    {
        //TODO: add multiple copy support
        return (selection.size() == 1 && selection.getFirstElement() instanceof Node);
    }

    public void run(IStructuredSelection selection)
    {
        if (selection.size() > 1 || selection.size() <= 0)
        {
            return;
        }
        String text = "";
        if (selection.getFirstElement() instanceof Node)
        {
            text = ((Node) selection.getFirstElement()).getSQLText();
        }
        else
        {
            return;
        }
        copyToClipboard(text, 0);
        // update the enablement of the paste action
        // workaround since the clipboard does not support callbacks
        if (_fPasteAction != null && _fPasteAction.getSelection() != null)
        _fPasteAction.update(_fPasteAction.getSelection());

    }

    private void copyToClipboard(String text, int repeatCount)
    {
        try
        {
            _fClipboard.setContents(new String[] 
            {
                text
            }
            , new Transfer[] 
            {
                TextTransfer.getInstance()
            }
            );
        }
        catch (SWTError e)
        {
            if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD || repeatCount >= _MAX_REPEAT_COUNT)
            throw e;

            if (MessageDialog
            .openQuestion(
                getShell(),
                Messages.getString("CopyToClipboard.error.title"), Messages.getString("CopyToClipboard.error.message"))) //$NON-NLS-1$ //$NON-NLS-2$
                copyToClipboard(text, repeatCount + 1);
        }
    }
}