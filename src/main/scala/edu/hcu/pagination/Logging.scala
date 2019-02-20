package edu.hcu.pagination

import org.slf4j.{Logger, LoggerFactory}

trait Logging {

  protected val logger: Logger = LoggerFactory.getLogger(this.getClass())
  protected def info(message: String): Unit = logger.info(message)

}
