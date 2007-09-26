/*******************************************************************************
 * Copyright � 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.datatools.sqltools.sqlbuilder.views.update;

import org.eclipse.core.resources.IFile;
import org.eclipse.datatools.modelbase.sql.query.QueryCombined;
import org.eclipse.datatools.modelbase.sql.query.QueryExpressionBody;
import org.eclipse.datatools.modelbase.sql.query.QuerySelect;
import org.eclipse.datatools.modelbase.sql.query.QuerySelectStatement;
import org.eclipse.datatools.modelbase.sql.query.QueryStatement;
import org.eclipse.datatools.modelbase.sql.query.QueryUpdateStatement;
import org.eclipse.datatools.modelbase.sql.query.QueryValueExpression;
import org.eclipse.datatools.modelbase.sql.query.SQLQueryObject;
import org.eclipse.datatools.modelbase.sql.query.UpdateAssignmentExpression;
import org.eclipse.datatools.modelbase.sql.query.UpdateSource;
import org.eclipse.datatools.modelbase.sql.query.UpdateSourceExprList;
import org.eclipse.datatools.modelbase.sql.query.UpdateSourceQuery;
import org.eclipse.datatools.modelbase.sql.query.ValueExpressionColumn;
import org.eclipse.datatools.modelbase.sql.query.helper.StatementHelper;
import org.eclipse.datatools.sqltools.parsers.sql.SQLParserException;
import org.eclipse.datatools.sqltools.parsers.sql.SQLParserInternalException;
import org.eclipse.datatools.sqltools.sqlbuilder.Messages;
import org.eclipse.datatools.sqltools.sqlbuilder.dialogs.MultilineInputDialog;
import org.eclipse.datatools.sqltools.sqlbuilder.expressionbuilder.ExpressionBuilderDialog;
import org.eclipse.datatools.sqltools.sqlbuilder.expressionbuilder.ExpressionBuilderWizard;
import org.eclipse.datatools.sqltools.sqlbuilder.model.ExpressionHelper;
import org.eclipse.datatools.sqltools.sqlbuilder.model.SQLBuilderConstants;
import org.eclipse.datatools.sqltools.sqlbuilder.model.SQLDomainModel;
import org.eclipse.datatools.sqltools.sqlbuilder.model.SQLStringHelper;
import org.eclipse.datatools.sqltools.sqlbuilder.model.SelectHelper;
import org.eclipse.datatools.sqltools.sqlbuilder.model.UpdateHelper;
import org.eclipse.datatools.sqltools.sqlbuilder.model.VendorHelper;
import org.eclipse.datatools.sqltools.sqlbuilder.util.WorkbenchUtility;
import org.eclipse.swt.widgets.Display;

public class UpdateTreeElement
{
  protected QueryUpdateStatement updateStmt;
  protected UpdateSource updateSource;
  protected ValueExpressionColumn column;
  protected SQLQueryObject valueSource;
  protected SQLDomainModel domainModel;
  protected boolean hasChildren;

  /**
   * @param update - the current full select statement
   * @param updateList - the AssignmentExpression that points to the current query statement
   */
  public UpdateTreeElement(QueryUpdateStatement update, UpdateSource updateSrc,
                                ValueExpressionColumn col, SQLQueryObject valueSource,
                                SQLDomainModel domainModel,boolean hasChildren)
  {
    this.updateStmt = update;
    updateSource =  updateSrc;
    this.valueSource = valueSource;
    this.column = col;
    this.domainModel = domainModel;
    this.hasChildren = hasChildren;
  }

  public QueryUpdateStatement getUpdateStatement()
  {
    return updateStmt;
  }

 
  public UpdateSource getUpdateSource()
  {
  	return updateSource;
  }
  
  public ValueExpressionColumn getRDBColumn()
  {
    return column;
  }

  public boolean hasChildren()
  {
  	return hasChildren;
  }

  /**
   * Get the MOF object value
   */
  public String getColumnText(int columnIndex)
  {
    String columnText="";
    //if it is the ValueExpression column 
    if (columnIndex == 0)
    {
      if (column != null)
      {
      	columnText = column.getSQL();
      }
      else
      {
      	columnText = Messages._UI_COLUMN_UPDATE_GROUP;
      }
    }
    //if it is a QueryValueExpression or QueryExpressionBody
    else if (columnIndex == 1)
    {
    	if (updateSource != null)
    	{
	      	VendorHelper vHelper = new VendorHelper(domainModel.getDatabase());
	        if ((column==null && updateSource instanceof UpdateSourceQuery)  ||
	            (column != null && vHelper.isSybase()))
	        {
	        	columnText = SQLStringHelper.trimBlanks(((UpdateSourceQuery) updateSource).getQueryExpr().getSQL());
	        }        
	        else if (valueSource != null)
	        {
	          columnText = valueSource.getSQL();
        }
      }
     
    }
  	 return columnText;
  }

  public void modify(Object key, Object propValue) {
  	
  	if (key == SQLBuilderConstants.P_EXPRESSION) {
  		SQLQueryObject newSource = null;
  		
  		if (propValue instanceof String) {
  			if (propValue.equals(SQLBuilderConstants.P_ADD_SELECT)
  					|| propValue.equals(SQLBuilderConstants.P_ADD_FULLSELECT)) {
  				
  				if (propValue.equals(SQLBuilderConstants.P_ADD_SELECT)) {
  					QuerySelect select = StatementHelper.createQuerySelect();
  					UpdateAssignmentExpression assignExpr = updateSource.getUpdateAssignmentExpr();
  					// Update model
  					UpdateHelper.createUpdateSourceQuery(assignExpr,select);
  				}
  				else if (propValue.equals(SQLBuilderConstants.P_ADD_FULLSELECT)) {
  					QueryCombined qCombined = StatementHelper.createQueryCombined();
  					QuerySelect leftQSelect = StatementHelper.createQuerySelect() ;
  					QuerySelect rightQSelect = StatementHelper.createQuerySelect() ;
  					qCombined.setLeftQuery(leftQSelect) ;
  					qCombined.setRightQuery(rightQSelect) ;
  					UpdateAssignmentExpression assignExpr = updateSource.getUpdateAssignmentExpr();
  					// Update model
  					UpdateHelper.createUpdateSourceQuery(assignExpr,qCombined);
  				}
  			} else if (propValue.equals(SQLBuilderConstants.P_BUILD_EXPRESSION)
  					|| propValue.equals(SQLBuilderConstants.P_REPLACE_EXPRESSION)
					|| propValue.equals(SQLBuilderConstants.P_EDIT_EXPRESSION)) {
  				newSource = showExpressionBuilder(valueSource, false,(String) propValue);
  			}
            else if (propValue.equals(SQLBuilderConstants.P_EDIT_INPUT_VALUE)) {                 
                newSource = showInputEditor(valueSource.getSQL());
            }
  		} else if (propValue instanceof QueryValueExpression) {
  			newSource = (QueryValueExpression) propValue;
  		} else if (propValue instanceof IFile) {
  			// TODO: Doesn't work correctly for WITH statements
  			IFile query = (IFile)propValue;
  			if (query != null) {
  				String fileContent = (WorkbenchUtility.readFileContentsToString(query, true)).trim();
  				QueryStatement stmt;
				try {
					stmt = domainModel.parse(fileContent, true);
				} catch (SQLParserException e) {
					SQLDomainModel.showParseErrors(e);
					return;
				} catch (SQLParserInternalException e) {
					SQLDomainModel.showParseErrors(e);
					return;
				}
  				QueryExpressionBody queryExprBody = SelectHelper.getQueryExpressionBody((QuerySelectStatement) stmt);
  				UpdateAssignmentExpression assignExpr = updateSource.getUpdateAssignmentExpr();
					// Update model
				UpdateHelper.createUpdateSourceQuery(assignExpr,queryExprBody);
  			}
  		}
  		
  		// Update model with simple value expression, if needed
  		if (newSource != null) {
  			valueSource = newSource;
  			//  Modify value in the original updatestatement.
  			if (newSource instanceof QueryValueExpression) {
  				if (updateSource instanceof UpdateSourceExprList) {
  					UpdateHelper.setValueForColumn(
  							(UpdateSourceExprList) updateSource, column,
							(QueryValueExpression) newSource);
  				}
  				else {
  					// Must be an UpdateQuerySource - need to make
  					// a new UpdateSourceExpressionList
  					UpdateAssignmentExpression assignExpr = updateSource.getUpdateAssignmentExpr();
  					// Update model
  					UpdateHelper.createUpdateSourceExpressionList(assignExpr, column,
  							(QueryValueExpression) newSource);
  				}
  			}
  		}
  		//  TODO: Hack to get the Outline view to update
  		// Needed to display subqueries from UpdateQuerySource and UpdateSourceExpressionLists
	    getUpdateStatement().setTargetTable(getUpdateStatement().getTargetTable());	
  	}
  }


  /**
    * Launch the expression builder
    */
    public QueryValueExpression showExpressionBuilder(Object obj, boolean isColumn, String action)
    {
      
      QueryValueExpression expr = null;
      ExpressionBuilderWizard  wizard = new ExpressionBuilderWizard(domainModel, domainModel.getSQLStatement());
      if (obj instanceof QueryValueExpression)
      {
        if (action.equals(SQLBuilderConstants.P_EDIT_EXPRESSION))
        {
          if (obj != null)
          {
            wizard.setInputExpression((QueryValueExpression)obj);
          }
          else
          {
            wizard.setInputExpression(null);
          }
        }
      }
      wizard.setIsColumn(isColumn);

      ExpressionBuilderDialog dialog = new ExpressionBuilderDialog(Display.getDefault().getActiveShell(), wizard);

      dialog.create();
      dialog.setBlockOnOpen(true);
      int result = dialog.open();

      if (result == 0)
      {
        expr = wizard.getSQLExpression();
      }
          return expr; 
    }
    
    /**
     * Launches the input editor to optain user value
     * @param initialText the initial text to show in the editor
     * @return the input from the editor as a QueryValueExpression
     */
    protected QueryValueExpression showInputEditor(String initialText) {
    	MultilineInputDialog inputDialog = new MultilineInputDialog(Display.getDefault().getActiveShell(), 
    			Messages._UI_SPECIFY_VALUE_TITLE, column.getName());
    	inputDialog.setText(initialText);
    	inputDialog.open();
    	String text = inputDialog.getText();
        QueryValueExpression expression = ExpressionHelper.createExpression(text);
        return expression;
    }
}
