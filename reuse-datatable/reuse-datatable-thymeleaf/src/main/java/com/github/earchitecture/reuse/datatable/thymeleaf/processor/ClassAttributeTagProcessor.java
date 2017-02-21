package com.github.earchitecture.reuse.datatable.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public class ClassAttributeTagProcessor extends AbstractAttributeTagProcessor {
  private static final String ATTR_NAME = "table";
  private static final int PRECEDENCE = 10000;

  public ClassAttributeTagProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.thymeleaf.processor.element.AbstractAttributeTagProcessor#doProcess(org.thymeleaf.context.ITemplateContext,
   * org.thymeleaf.model.IProcessableElementTag, org.thymeleaf.engine.AttributeName, java.lang.String,
   * org.thymeleaf.processor.element.IElementTagStructureHandler)
   */
  @Override
  protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
    if (attributeValue != null && Boolean.parseBoolean(attributeValue)) {
      String cssClass = tag.getAttributeValue("class");
      if (cssClass != null) {

      } else {
        structureHandler.setAttribute("class", "table table-striped");
      }
    }
  }

}
