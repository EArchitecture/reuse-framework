package com.github.earchitecture.reuse.datatable.thymeleaf.processor;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class CsrfAttributeModelProcessor extends AbstractAttributeModelProcessor implements IDataTableConfig {
  private static final String ATTR_NAME = "csrf";
  private static final int PRECEDENCE = 0;
  private StringBuffer dataConfig;

  public CsrfAttributeModelProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
  }

  @Override
  public String getDataConfig() {
    return this.dataConfig.toString();
  }

  @Override
  protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
    if (attributeValue != null && StringUtils.isNotBlank(attributeValue)) {
      this.dataConfig = new StringBuffer();
      this.dataConfig.append("'data': {").append("\n");
      this.dataConfig.append("  '").append(attributeValue).append("':").append("$('meta[name=").append(attributeValue).append("]').attr('content')").append("\n");
      this.dataConfig.append("}");
    }
  }
}
