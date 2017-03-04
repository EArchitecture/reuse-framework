package com.github.earchitecture.reuse.datatable.thymeleaf.processor;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class PropertyAttributeModelProcessor extends AbstractAttributeModelProcessor implements IDataTableConfig {
  private static final String ATTR_NAME = "property";
  private static final int PRECEDENCE = 0;
  private StringBuffer config;

  public PropertyAttributeModelProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
  }

  @Override
  public String getDataConfig() {
    return this.config != null ? this.config.toString() : null;
  }

  @Override
  protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
    if (StringUtils.isNotBlank(attributeValue)) {
      config = new StringBuffer();
      config.append("\n").append("{'data':'").append(attributeValue).append("'}");
    }
  }
}
