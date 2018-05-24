/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.prosmart.JSHandlers;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Sinisa
 */
public class BrowserPane extends BorderPane {
    private static String DEFAULT_HOME_PAGE = "http://www.google.com";
    private WebView webView;
    
    public BrowserPane(Window ownerWindow) {
        this(null, ownerWindow);
    }
    
    public BrowserPane(String homePageUrl, Window ownerWindow)
    {
        this(homePageUrl, true, true, true, ownerWindow);
    }
    
    public BrowserPane(String homePageUrl, 
                       boolean enableNavigationBar,
                       boolean enableStatusBar,
                       boolean enableJSHandlers,
                       Window ownerWindow)
    {
        webView = new WebView();
        this.setCenter(webView);
        
        if(homePageUrl == null) {
            homePageUrl = DEFAULT_HOME_PAGE;
        }
        
        if(enableNavigationBar) {
            this.addNavigationBar(homePageUrl);
        }
        
        if(enableStatusBar) {
            this.addStatusBar();
        }
        
        if(enableJSHandlers) {
            this.enableJSHandlers(ownerWindow);
        }
    }

    private void addNavigationBar(String homePageUrl) {
        
    }

    private void addStatusBar() {
        
    }

    private void enableJSHandlers(Window ownerWindow) {
        webView.getEngine().setPromptHandler(JSHandlers.getPromptHandler());
        webView.getEngine().setCreatePopupHandler(JSHandlers.getPopupHandler());
        webView.getEngine().setOnAlert(JSHandlers::alertHandler);
        webView.getEngine().setConfirmHandler(JSHandlers.getConfirmHandler());
        if(ownerWindow instanceof Stage)
        {
            Stage stage = (Stage) ownerWindow;
            webView.getEngine().titleProperty().addListener((prop, oldTitle, newTitle) -> stage.setTitle(newTitle));
        }        
    }
    
    public WebView getWebView()
    {
        return webView;
    }

    private static class WebOptionsMenu extends MenuButton {

        public WebOptionsMenu(WebView webView) {
            this.getItems().add(new MenuItem("aaaa"));
            this.getItems().add(new MenuItem("bbbb"));
        }
    }
}
