mudar diretório da indexação do lucene:
  /home/igorribeirolima/indexacao_lucene/noticias/  
mudar diretório do log:
  /usr/local/shared/tomcat/igorribeirolima/logs/
  ${catalina.home}/logs/
log4j:
  comentar log4j.logger.org.hibernate=debug
  retirar impressao no stdout
alterar porta da conexão do bd:
  porta 3307