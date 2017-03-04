package com.github.earchitecture.reuse.datatable.thymeleaf.processor;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.util.Validate;

public class ServerSideAttributeModelProcessor extends AbstractAttributeModelProcessor implements IDataTableConfig {
  private static final String ATTR_NAME = "serverside";
  private static final int PRECEDENCE = 0;
  private StringBuffer configuracao;

  public ServerSideAttributeModelProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
  }

  @Override
  protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
    this.configuracao = new StringBuffer();
    if (attributeValue != null && Boolean.parseBoolean(attributeValue)) {
      this.configuracao.append("'processing': true,").append("\n");
      this.configuracao.append("'serverSide': true,").append("\n");
      if (model != null && model.size() > 0) {
        IOpenElementTag table = (IOpenElementTag) model.get(0);
        UrlAttributeModelProcessor urlPro = this.extractUrl(context, model, structureHandler, table);
        CsrfAttributeModelProcessor csrfPro = this.extractCsrf(context, model, structureHandler, table);
        this.configuracao.append("'ajax':{").append("\n");
        this.configuracao.append(urlPro.getDataConfig());
        if (StringUtils.isNotBlank(csrfPro.getDataConfig())) {
          this.configuracao.append(",").append("\n").append(csrfPro.getDataConfig()).append("\n");
        }
        this.configuracao.append("}").append("\n");
      }
    }
  }

  /**
   * Estrai url
   * 
   * @param context
   *          context
   * @param model
   *          model element
   * @param structureHandler
   *          structureHandler
   * @param table
   * @return Url processor
   */
  private UrlAttributeModelProcessor extractUrl(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler, IOpenElementTag table) {
    IAttribute attr = table.getAttribute(super.getDialectPrefix() + ":url");
    Validate.notNull(attr, "Attribute url cannot be null or empty in Server Side Processor");
    Validate.notEmpty(attr.getValue(), "Attribute url cannot be null or empty in Server Side Processor");
    UrlAttributeModelProcessor urlPro = new UrlAttributeModelProcessor(super.getDialectPrefix());
    urlPro.process(context, model, structureHandler);
    return urlPro;
  }

  private CsrfAttributeModelProcessor extractCsrf(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler, IOpenElementTag table) {
    IAttribute attr = table.getAttribute(super.getDialectPrefix() + ":csrf");
    Validate.notNull(attr, "Attribute url cannot be null or empty in Server Side Processor");
    Validate.notEmpty(attr.getValue(), "Attribute url cannot be null or empty in Server Side Processor");
    CsrfAttributeModelProcessor csrfPro = new CsrfAttributeModelProcessor(super.getDialectPrefix());
    csrfPro.process(context, model, structureHandler);
    return csrfPro;
  }

  @Override
  public String getDataConfig() {
    return this.configuracao.toString();
  }
}
