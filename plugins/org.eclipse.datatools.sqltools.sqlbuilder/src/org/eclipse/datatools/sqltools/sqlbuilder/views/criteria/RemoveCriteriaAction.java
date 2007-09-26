/*
 * Licensed Materials - Property of IBM
 * org.eclipse.datatools.sqltools.sqlbuilder
 * (C) Copyright IBM Corporation 2000, 2005. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:
 *   Use, duplication or disclosure restricted 
 *   by GSA ADP Schedule Contract with IBM Corp.
 */
package org.eclipse.datatools.sqltools.sqlbuilder.views.criteria;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import org.eclipse.datatools.modelbase.sql.query.Predicate;
import org.eclipse.datatools.modelbase.sql.query.QuerySearchCondition;
import org.eclipse.datatools.modelbase.sql.query.QuerySelect;
import org.eclipse.datatools.modelbase.sql.query.QuerySelectStatement;
import org.eclipse.datatools.modelbase.sql.query.SQLQueryObject;
import org.eclipse.datatools.sqltools.sqlbuilder.Messages;
import org.eclipse.datatools.sqltools.sqlbuilder.model.SelectHelper;

/**
 * Remove the selected criteria from the MOF model
 */
public class RemoveCriteriaAction extends Action {

    TableViewer gridViewer;

    public RemoveCriteriaAction(TableViewer gridViewer) {
        super(Messages._UI_ACTION_REMOVE_CONDITION);
        this.gridViewer = gridViewer;
    }

    public void run() {
        ISelection selection = gridViewer.getSelection();

        if (selection.isEmpty() || !(selection instanceof IStructuredSelection))
            return;

        IStructuredSelection es = (IStructuredSelection) selection;
        Iterator elements = es.iterator();

        gridViewer.cancelEditing();

        while (elements.hasNext()) {
            Object item = elements.next();

            if (item instanceof CriteriaElement) {
                removeCriteria((CriteriaElement) item);
            }
        }
    }

    private void removeCriteria(CriteriaElement criteriaElement) {
        QuerySearchCondition searchCondition = criteriaElement.getSearchCondition();
        Predicate cond = criteriaElement.getCurrentPredicate();
        SQLQueryObject currStmt = criteriaElement.getSQLStatement();

        if (searchCondition != null && cond != null) {
            criteriaElement.getSearchConditionHelper().removePredicateFromCondition(cond, searchCondition, currStmt);
            if (currStmt instanceof QuerySelectStatement || currStmt instanceof QuerySelect) {
                SelectHelper.refresh(currStmt);
            }            
            return;
        }        
    }
}
