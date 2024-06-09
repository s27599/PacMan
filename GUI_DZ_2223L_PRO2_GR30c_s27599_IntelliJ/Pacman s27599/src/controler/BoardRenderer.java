package controler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import model.*;

public class BoardRenderer extends JTable implements TableCellRenderer {
private Direction direction;

    public BoardRenderer(Direction direction) {
        super.setOpaque(true);
        this.direction = direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


private JLabel imgRender(JTable table, File file, Direction direction) {
    JLabel picLabel = new JLabel();
    try {
        ImageIcon icon = new ImageIcon(ImageIO.read(file));
        Image image = icon.getImage();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(direction.getValue() * 90), image.getWidth(null) / 2, image.getHeight(null) / 2);
        BufferedImage rotatedImg = new BufferedImage(image.getHeight(null), image.getWidth(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImg.createGraphics();
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        Image scaledImg = rotatedImg.getScaledInstance(table.getColumnModel().getTotalColumnWidth() / table.getColumnCount(), -1, Image.SCALE_SMOOTH); //-1 = skalowanie proporcjonalne
        picLabel.setIcon(new ImageIcon(scaledImg));
    } catch (IOException e) {
        picLabel.setText("brak obrazu");
    }
    return picLabel;
}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        switch ((Integer) value) {
            case 0 -> super.setBackground(Color.WHITE);
            case 1 -> super.setBackground(Color.BLACK);
            case 2 -> {


                return imgRender(table,new File("player.png"),direction);
            }
            case 3 -> {
                return imgRender(table,new File("playerClosed.png"),direction);
            }
            case 4 -> {
                return imgRender(table,new File("scaryCharacter.png"), Direction.NA);
            }
            case 5 -> {
                return imgRender(table,new File("point.png"), Direction.NA);
            }
            case 6 ->{
                return imgRender(table,new File("Bigpoint.png"), Direction.NA);
            }
        }

        return this;
    }
}
