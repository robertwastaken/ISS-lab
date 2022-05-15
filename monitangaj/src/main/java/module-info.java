module ro.ubbcluj.map.mavenfx2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens ro.ubbcluj.map.monitangaj to javafx.fxml;
    exports ro.ubbcluj.map.monitangaj;
    exports ro.ubbcluj.map.monitangaj.controllers;
    exports ro.ubbcluj.map.monitangaj.domain;
    exports ro.ubbcluj.map.monitangaj.service;
    exports ro.ubbcluj.map.monitangaj.authentication;
    opens ro.ubbcluj.map.monitangaj.controllers to javafx.fxml;
}