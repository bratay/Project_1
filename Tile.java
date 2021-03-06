import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Tile extends JButton {
    /* Contants for Tiles */
    private static final int mTileSize = 30;

    /* Member variables of tiles */
    private int mSurroundingMines;
    private boolean mFlagged;
    private boolean mIsMine;
    private boolean mOpened;
    private int x;
    private int y;

    /* The ImageIcons used to display the icons */
    private static ImageIcon mFlaggedIcon;
    private static ImageIcon mMineIcon;
    private static ImageIcon mTileIcon;
    private static ImageIcon mPressedIcon;

    //constructor
    Tile() {
        super();


        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine = false;
        setText("");
        setMargin(new Insets(0, 0, 0, 0));
        setPreferredSize(new Dimension(mTileSize, mTileSize));
        setIcon(mTileIcon);
        setSize(mTileSize, mTileSize);
        setVisible(true);
      
        this.addMouseListener(mouseListener);
    }

    /////////////////////////////////////////////////////////
    //Getters
    /////////////////////////////////////////////////////////

    public boolean getFlagged() {
        return mFlagged;
    }

    Integer getSurroundingMines() {
        return mSurroundingMines;
    }

    boolean getIsMine() {
        return mIsMine;
    }

    public boolean getIsOpened() {
        return mOpened;
    }

    boolean canOpen() {
        return (!mOpened);
    }

    /////////////////////////////////////////////////////////
    //Setters
    /////////////////////////////////////////////////////////

    public void setNullIcon(){
        this.setIcon(null);
        setDisable();
    }

    public void setDisable(){
        this.setEnabled(false);
        this.removeMouseListener(mouseListener);
    }

    public void setFlagged(boolean flagged){
        if(flagged){
            this.setIcon(mFlaggedIcon);
            Board.incrementDownFlagCount();
        }else{
            this.setIcon(mTileIcon);
            Board.incrementUpFlagCount();
        }
    }
  
    public void setIcons() {
        /* Generating the ImageIcons using ImageIO.read */
        try {
            Image img = null;
            try {
                img = ImageIO.read(getClass().getResource("Resources/flag.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mFlaggedIcon = new ImageIcon(img);

            try {
                img = ImageIO.read(getClass().getResource("Resources/MineIcon.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mMineIcon = new ImageIcon(img);

            try {
                img = ImageIO.read(getClass().getResource("Resources/TileIcon.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mTileIcon = new ImageIcon(img);

            try {
                img = ImageIO.read(getClass().getResource("Resources/PressedIcon.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            mPressedIcon = new ImageIcon(img);


        } catch (Exception e) {
            System.out.println("ImageIcons not set successfully.");
        }
    }

    public void setMine(){
        setIcon(mMineIcon);
    }

    void setX(int i){
        x = i;
    }

    void setY(int j){
        y = j;
    }

    public void setFlagged(boolean flagged) {
        if (flagged) {
            this.setIcon(mFlaggedIcon);
        } else {
            this.setIcon(mTileIcon);
        }
    }

    void setSurroundingMines(int mineCount) {
        mSurroundingMines = mineCount;
    }

    void setIsMine(boolean isMine) {
        mIsMine = isMine;
    }

    /////////////////////////////////////////////////////////
    //METHODS
    /////////////////////////////////////////////////////////

    void increaseSurroundingMines() {
        mSurroundingMines += 1;
    }

    //sets the text on the tile showing how many mines are near
    void displaySurroundingMines(){
        //we will not display 0 for number of mines

        if (mSurroundingMines == 0) {
            setIcon(mPressedIcon);
        } else if (mSurroundingMines > 0) {
            try {
                setIcon(null);
                setText(Integer.toString(mSurroundingMines));
            } catch (Exception e) {
                System.out.println("Error in displaying numbered tile.");
            }
        }
        setDisable();
    }

    MouseListener mouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {
            int modifiers = mouseEvent.getModifiers();
            //left button clicked
            if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                if(mIsMine){
                    setMineImage(true);
                    Game_Driver.gameOver();
                }else{
                    if(mFlagged)
                        setFlagged(!mFlagged);

                    if(mSurroundingMines > 0){
                        displaySurroundingMines();
                    }else{
                        setNullIcon();
                        Game_Driver.revealExpanding(x,y);
                    }
                }
            }
            //right button clicked
            if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
              setFlagged(!mFlagged);
            }
        }
    };

    public void cleanTile() {
        mSurroundingMines = 0;
        mFlagged = false;
        mIsMine = false;
        setIcon(mTileIcon);
    }

    void setIsOpened() {
        mOpened = true;
    }
}