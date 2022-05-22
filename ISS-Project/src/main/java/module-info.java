module com.example.issproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.issproject to javafx.fxml;
    exports com.example.issproject;

    exports domain;
    exports service;

    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires com.fasterxml.classmate;
    requires net.bytebuddy;
    requires jakarta.xml.bind;
    requires org.glassfish.jaxb.core;
    requires org.glassfish.jaxb.runtime;
}