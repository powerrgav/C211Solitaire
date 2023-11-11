public class Card {
    int value;
    char suit;
    //This attribute may be unneccesary, can just have if statement on return for getColor()
    char color;

    Card(int val, char suitChar){
        value = val;
        suit = suitChar;
        if(suitChar == '♣' || suitChar == '♦')
            color = 'B';
        else
            color = 'R';
    }

    public char getSuit(){
        return suit;
    }

    public char getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String getFace(){
        String faceAsString = "";
        if( value > 1 && value < 11)
            faceAsString = String.format("%d%s", value, suit);
        else{
            switch(value){
                case 1:
                    faceAsString = "A" + suit;
                    break;
                case 11:
                    faceAsString = "J" + suit;
                    break;
                case 12:
                    faceAsString = "Q" + suit;
                    break;
                case 13:
                    faceAsString = "K" + suit;;
                    break;
            }
        }
        return faceAsString;
    }
}
