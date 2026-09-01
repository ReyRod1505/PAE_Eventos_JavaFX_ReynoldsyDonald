module ni.edu.uam.pae_eventos_javafx_reynoldsydonald {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.pae_eventos_javafx_reynoldsydonald to javafx.fxml;
    opens ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto2.controlador to javafx.fxml;

    opens ni.edu.uam.pae_eventos_javafx_reynoldsydonald.reto2.modelo to javafx.base;

    exports ni.edu.uam.pae_eventos_javafx_reynoldsydonald;
}