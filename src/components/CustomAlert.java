package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;

import raven.glasspanepopup.GlassPanePopup;

public class CustomAlert extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    
    private CustomButton btnCancel;
    private CustomButton btnAccept;
    private JLabel lblAlertTitle;
    private JTextPane txtAlertMessage;

    public CustomAlert() {
        initComponents();
        setOpaque(false);
        txtAlertMessage.setBackground(new Color(0, 0, 0, 0));
        txtAlertMessage.setSelectionColor(new Color(48, 170, 63, 200));
        txtAlertMessage.setOpaque(false);
        
        setButtonColor(btnAccept, new Color(30, 180, 114), new Color(30, 180, 114));
        setButtonColor(btnCancel, new Color(253, 83, 83), new Color(253, 83, 83));
    }
    
    public static void showAlert(String title, String alertMessage, String type) {
        CustomAlert alertPanel = new CustomAlert();
        alertPanel.setAlertTitleText(title);
        alertPanel.setAlertMessageText(alertMessage);
        alertPanel.showCancelButton(false);

        if ("error".equals(type)) {
            alertPanel.setButtonColor(alertPanel.btnAccept, new Color(253, 83, 83), new Color(253, 83, 83));
        } else if ("success".equals(type)) {
            alertPanel.setButtonColor(alertPanel.btnAccept, new Color(30, 180, 114), new Color(30, 180, 114));
        }

        GlassPanePopup.showPopup(alertPanel);
    }

    public static void showAlertWithAction(String title, String alertMessage, String type, ActionListener acceptAction) {
        CustomAlert alertPanel = new CustomAlert();
        alertPanel.setAlertTitleText(title);
        alertPanel.setAlertMessageText(alertMessage);
        alertPanel.showCancelButton(false);

        if ("error".equals(type)) {
            alertPanel.setButtonColor(alertPanel.btnAccept, new Color(253, 83, 83), new Color(253, 83, 83));
        } else if ("success".equals(type)) {
            alertPanel.setButtonColor(alertPanel.btnAccept, new Color(30, 180, 114), new Color(30, 180, 114));
        }

        alertPanel.setAcceptAction(acceptAction);

        GlassPanePopup.showPopup(alertPanel);
    }

    public static void showConfirmationAlert( String title, String alertMessage, ActionListener acceptAction, ActionListener cancelAction) {
        CustomAlert confirmAlertPanel = new CustomAlert();
        confirmAlertPanel.setAlertTitleText(title);
        confirmAlertPanel.setAlertMessageText(alertMessage);
        confirmAlertPanel.showCancelButton(true);
        
        confirmAlertPanel.setButtonColor(confirmAlertPanel.btnAccept, new Color(30, 180, 114), new Color(30, 180, 114));

        confirmAlertPanel.setAcceptAction(acceptAction);
        confirmAlertPanel.setCancelAction(cancelAction);

        GlassPanePopup.showPopup(confirmAlertPanel);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(245, 245, 245));
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        g2.dispose();
        super.paintComponent(grphcs);
    }

    private void initComponents() {
        lblAlertTitle = new JLabel();
        txtAlertMessage = new JTextPane();
        txtAlertMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancel = new CustomButton();
        btnCancel.setFont(new Font("Arial", Font.BOLD, 12));
        btnAccept = new CustomButton();
        btnAccept.setFont(new Font("Arial", Font.BOLD, 12));

        setBackground(new Color(43, 43, 43));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        lblAlertTitle.setFont(new Font("Arial", Font.BOLD, 17)); 
        lblAlertTitle.setForeground(new Color(50, 50, 50));
        lblAlertTitle.setText("Título");

        txtAlertMessage.setEditable(false);
        txtAlertMessage.setForeground(new Color(100, 100, 100));
        txtAlertMessage.setText("Mensaje");

        btnAccept.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAccept.setText("Aceptar");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });
        
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAlertTitle)
                        .addGap(0, 261, Short.MAX_VALUE))
                    .addComponent(txtAlertMessage, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAccept, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAlertTitle)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAlertMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccept, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {
        GlassPanePopup.closePopupLast();
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        GlassPanePopup.closePopupLast();
    }

    public void setAcceptAction(ActionListener action) {
        for (ActionListener al : btnAccept.getActionListeners()) {
            btnAccept.removeActionListener(al);
        }

        btnAccept.addActionListener(action);
    }
    
    public void setCancelAction(ActionListener action) {
        btnCancel.addActionListener(action);
    }

    public void setAlertMessageText(String text) {
        txtAlertMessage.setText(text);
    }

    public void setAlertTitleText(String title) {
        lblAlertTitle.setText(title);
    }

    public void setButtonColor(CustomButton button, Color backgroundColor, Color shadowColor) {
        button.setBackground(backgroundColor);
        button.setForeground(new Color(245, 245, 245));
        button.setRippleColor(new Color(255, 255, 255));
        button.setShadowColor(shadowColor);
    }

    public void showCancelButton(boolean show) {
        btnCancel.setVisible(show);
    }
}
