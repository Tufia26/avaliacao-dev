# Sistema SOC - Gestão de Agendamentos

Sistema web para gestão de consultas e agendamentos ocupacionais desenvolvido em Java com Struts 2, persistência em H2 Database e exportação de relatórios em Excel com Apache POI.

---

## 🛠️ Tecnologias

* Java 8
* Apache Struts 2
* H2 Database
* Apache POI (Excel)
* Maven / Eclipse Jetty Plugin

---

## 🚀 Como Executar

1. Abra o **Eclipse** e certifique-se de que o projeto **avaliacao-dev** está importado como um projeto Maven.
2. Clique com o botão direito sobre o projeto **avaliacao-dev**.
3. Vá em **Run As > Maven Build...**.
4. Na janela de configurações que se abrir, localize o campo **Goals** e digite:
   ```text
   jetty:run
   ```
5. Clique em **Run**.
6. Aguarde o console exibir o status de inicialização concluída (`Started`).
7. Abra o navegador e acesse a URL:
   ```text
   http://localhost:8080/avaliacao
   ```

---

## 🗄️ Console do Banco H2

* **URL de Acesso:** `http://localhost:8080/avaliacao/h2-console`
* **JDBC URL:** `jdbc:h2:mem:avaliacao`
* **User Name:** *(em branco)*
* **Password:** *(em branco)*