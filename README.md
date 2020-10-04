# spring-boot

Casca de um projeto usando Spring Boot conectando em SQL Server.

Sou DBA SQL Server, então o desenvolvimento não é minha skill primária, então podem haver item não configurados da melhor forma possível, não recomendo uma implantação de um projeto basedo neste repo direto em produção, se o fizer é por sua conta e risco.

Esse projeto tem algumas implementações que acho minimamente necessárias para uma aplicação:

- Site em HTTPS: na pasta certificate tem todas as instuções de como gerei a esrutura para que o meu Google Chrome aceitasse o localhost como HTTPS.

- Autenticação LDAP: Simulando uma autenticação em um Active Directory, o Spring Boot sobe um servidor LDAP para teste, aonde username: ben e passsword: benspassword.

- String de conexão dinâmica: como eu precisava de um porta central que dele eu conseguisse conectar em outros SQL Server, implementei uma forma de poder escoler servidores para mandar comandos pré-definidos.

- Pooling de Conexão: como DBA não podia deixar este item de fora, todas as conexões respeitam as regras de pooling de conexão do application.properties.

- Front End: usei o Bootstrap para facilitar minha vida, afinal sou péssimo em layout.

- O projeto no POM.xml está com versões mais específicas de alguns componentes:

    - JVM 11: Versão LTS, além disso, compilei e executei com a OpenJDK 11 J9, se usar outra pode ser que tenha de ajustar algo.

    - JDBC SQL Server: setada versão fixa para não gerar problemas de colocar em uso uma versão beta.

    - Hikari: mesmo motivo do JDBC SQL Server.

Espero que ajude a quem quiser aprender.

Bons estudos.
