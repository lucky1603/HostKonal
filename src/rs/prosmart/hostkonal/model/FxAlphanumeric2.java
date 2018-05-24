package rs.prosmart.hostkonal.model;

import controllz.mmi.elements.Alphanumeric;
import java.text.DecimalFormat;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class FxAlphanumeric2 extends Group {
	private Rectangle rectShape;
	private Text textShape;
	
	private SimpleDoubleProperty x = new SimpleDoubleProperty();
	private SimpleDoubleProperty y = new SimpleDoubleProperty();
	private SimpleDoubleProperty width = new SimpleDoubleProperty();
	private SimpleDoubleProperty height = new SimpleDoubleProperty();	
	
	private SimpleStringProperty content = new SimpleStringProperty();
	private SimpleObjectProperty<Color> foregroundColor = new SimpleObjectProperty<Color>();
	private SimpleObjectProperty<Color> backgroundColor = new SimpleObjectProperty<Color>();
                	
        private SimpleDoubleProperty valueProperty = new SimpleDoubleProperty();
	
	/**
	 * 
	 * @return the X position.
	 */
	public double getX() {
		return x.get();
	}

	/**
	 * 
	 * @param x x position of the upper left corner.
	 */
	public void setX(double x) {
		this.x.set(x);;
	}
	
	/**
	 * @return Y position of the upper left corner.
	 */
	public double getY()
	{
		return y.get();
	}
	
	/**
	 * @param y Y position of the upper left corner.
	 */
	public void setY(double y)
	{
		this.y.set(y);
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width.get();
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width.set(width);
	}
	
	/**
	 * @return Height of the shape
	 */
	public double getHeight()
	{
		return height.get();
	}
	
	/**
	 * Sets hidth of the shape
	 * @param height Height of the shape
	 */
	public void setHeight(double height)
	{
		this.height.set(height);
	}
	
	/**
	 * Constructor
	 */
	public FxAlphanumeric2()
	{
		rectShape = new Rectangle();
		textShape = new Text();
		
		rectShape.xProperty().bind(this.x);
		rectShape.yProperty().bind(this.y);
		rectShape.widthProperty().bind(this.width);
		rectShape.heightProperty().bind(this.height);
		rectShape.fillProperty().bind(this.backgroundColor);
		rectShape.strokeProperty().bind(this.foregroundColor);
		
		textShape.xProperty().bind(this.x);
		textShape.yProperty().bind(this.y);
		textShape.textProperty().bind(this.content);		
		textShape.setTextOrigin(VPos.TOP);		
		textShape.fillProperty().bind(this.foregroundColor);
                                
//                StringExpression strBinding = Bindings.format("%04.3f", this.valueProperty);
//                strBinding.addListener((obs, oldVal, newVal) -> {
//                    this.content.set(newVal);
//                });
                
                
                		
		this.getChildren().addAll(rectShape, textShape);
	}
	
	/**
	 * Constructor
	 * @param alpha Alphanumeric element to initialize from.
	 */
	public FxAlphanumeric2(Alphanumeric alpha)
	{
		this();
		this.setLayoutX(alpha.getRoi().getX());
		this.setLayoutY(alpha.getRoi().getY());
		
		//setX(alpha.getRoi().getX());
		//setY(alpha.getRoi().getY());
		setWidth(alpha.getRoi().getWidth());
		setHeight(alpha.getRoi().getHeight());
		setContent(alpha.getValue().toString());
		
		Color bColor = Color.rgb(alpha.getBackground().getRed(), 
								 alpha.getBackground().getGreen(),
								 alpha.getBackground().getBlue(),
								 alpha.getBackground().getAlpha()/255.0);
		this.setBackgroundColor(bColor);
		
		Color fColor = Color.rgb(alpha.getForeground().getRed(), 
				 alpha.getForeground().getGreen(),
				 alpha.getForeground().getBlue(),
				 alpha.getForeground().getAlpha()/255.0);
		this.setForegroundColor(fColor);
                
                this.valueProperty.addListener((n, old, current) -> {
                    String formatString = "";
                    for(int i = 0; i < alpha.getNumdigits(); i++)
                    {
                        formatString += "0";
                    }

                    if(alpha.getFraction() > 0)
                    {
                        formatString += ".";
                        for(int j = 0; j < alpha.getFraction(); j++)
                        {
                            formatString += "0";
                        }
                    }

                    DecimalFormat formatter = new DecimalFormat(formatString);                    
                    this.content.set(formatter.format(current));                    
                });
		
                setValue(4.0);
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content.get();
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {            
            this.content.set(content);
	}

	/**
	 * @return the foregroundColor
	 */
	public Color getForegroundColor() {
		return foregroundColor.get();
	}

	/**
	 * @param foregroundColor the foregroundColor to set
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor.set(foregroundColor);
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor.get();
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor.set(backgroundColor);
	}
        
        /**
         * Sets the 
         * @param value 
         */
        public void setValue(Double value)
        {
            valueProperty.set(value);
        }
        
        public Double getValue()
        {
            return valueProperty.get();
        }
		
}
