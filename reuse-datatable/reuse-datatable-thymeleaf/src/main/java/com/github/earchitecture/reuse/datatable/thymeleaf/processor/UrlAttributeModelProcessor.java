package com.github.earchitecture.reuse.datatable.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class UrlAttributeModelProcessor extends AbstractAttributeModelProcessor implements IDataTableConfig {
  private static final String ATTR_NAME = "url";
  private static final int PRECEDENCE = 0;
  private StringBuffer dataConfig;

  public UrlAttributeModelProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
  }

  @Override
  public String getDataConfig() {
    return this.dataConfig.toString();
  }

  @Override
  protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
    dataConfig = new StringBuffer();
    IStandardExpression expresion = StandardExpressions.getExpressionParser(context.getConfiguration()).parseExpression(context, attributeValue);
    dataConfig.append("'url':'").append(expresion.execute(context).toString()).append("',").append("\n");
    dataConfig.append("'type':'POST'");
  }
}
