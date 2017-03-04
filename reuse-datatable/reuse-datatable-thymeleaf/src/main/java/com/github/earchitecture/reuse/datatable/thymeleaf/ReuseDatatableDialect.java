package com.github.earchitecture.reuse.datatable.thymeleaf;

import com.github.earchitecture.reuse.datatable.thymeleaf.processor.DataTableAttributeModelProcessor;
import com.github.earchitecture.reuse.datatable.thymeleaf.processor.InstallDataTableModelProcessor;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Configura componente de datatable.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public class ReuseDatatableDialect extends AbstractProcessorDialect {
  public static final String DIALECT_NAMESPACE = "http://www.thymeleaf.org/extras/reusedatatable";
  public static final String DIALECT_PREFIX = "dt";

  /**
   * Configura o com ponente para identificar a tag dt
   */
  public ReuseDatatableDialect() {
    super(DIALECT_NAMESPACE, DIALECT_PREFIX, 800);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.thymeleaf.dialect.IProcessorDialect#getProcessors(java.lang.String)
   */
  @Override
  public Set<IProcessor> getProcessors(String dialectPrefix) {
    final Set<IProcessor> processors = new HashSet<IProcessor>();
    processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
    processors.add(new InstallDataTableModelProcessor(dialectPrefix));
    processors.add(new DataTableAttributeModelProcessor(dialectPrefix));
    return processors;
  }

}
