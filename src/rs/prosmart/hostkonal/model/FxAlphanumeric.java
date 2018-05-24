package rs.prosmart.hostkonal.model;

import controllz.mmi.elements.Alphanumeric;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class FxAlphanumeric extends Text {
	private SimpleStringProperty elementId = new SimpleStringProperty();
	private SimpleStringProperty content = new SimpleStringProperty();
	
	public FxAlphanumeric(String content, int x, int y)
	{
		super(x, y, content);		
	}
	
	public FxAlphanumeric(Alphanumeric alpha)
	{
		super(alpha.getRoi().x, alpha.getRoi().y, alpha.getValueString());
		java.awt.Color originalForegroundColor = alpha.getForeground();
		java.awt.Color originalBackgroundColor = alpha.getBackground();
		Color convertedBackgroundColor = Color.rgb(originalBackgroundColor.getRed(), originalBackgroundColor.getGreen(), originalBackgroundColor.getBlue(), originalBackgroundColor.getAlpha() / 255.0);
		Color convertedForegroundColor = Color.rgb(originalForegroundColor.getRed(), originalForegroundColor.getGreen(), originalForegroundColor.getBlue(), originalForegroundColor.getAlpha() / 255.0);
		this.setFill(convertedForegroundColor);
		
		this.textProperty().bind(this.content);
		this.content.set(alpha.getValue().toString());
		this.setTextOrigin(VPos.TOP);
		this.setLayoutX(5);
	}

	public SimpleStringProperty getContentProperty() {
		return content;
	}

	public void setContent(String content)
	{
		this.content.set(content);
	}
	
	public String getContent()
	{
		return content.get();
	}
	
}
