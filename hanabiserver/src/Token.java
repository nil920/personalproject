public class Token {
    private static final String[] tokens = new String[]{
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u",
            "v","w","x","y","z"
    };


    public static String generatetoken(){
        String result = "";
        for (int i=0;i<3;i++){
            result+=tokens[(int)Math.round(Math.random()* (tokens.length-1))];
        }
        return result;
    }
}
