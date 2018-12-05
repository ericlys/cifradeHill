import java.util.Scanner;

public class CipherHill {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(" 1.para encriptar\n2.desencriptar\nescolha(1 ou 2):");
        int escolha = in.nextInt();
        in.nextLine();

        if (escolha == 1) {
            System.out.println("====enciptar====");
            cipherEncryption();
        }else if (escolha == 2){
            System.out.println("====desencriptar====");
            cipherDescryption();
        }else{
            System.out.println("escolha inválida");
        }
    }






    private static void cipherEncryption(){
        System.out.print("digite a mensagem:");
        String msg = in.nextLine();
        msg = msg.replaceAll("\\s", "");
        msg = msg.toUpperCase();

        //se o comprimento irregular, em seguida, executa o preenchimento
        int lenChk = 0;
        if (msg.length() % 2 != 0){
            msg += "0";
            lenChk = 1;
        }

        //mensagem para matrizes
        int[][] msg2D = new int[2][msg.length()];
        int itr1 = 0, itr2 = 0;
        for (int i = 0; i<msg.length(); i ++){
            if(i%2 == 0){
                msg2D[0][itr1] = ((int)msg.charAt(i)) - 65;
                itr1++;
            }else {
                msg2D[1][itr2] = ((int)msg.charAt(i)) - 65;
                itr2++;
            }
        }


        System.out.print("digite uma chave de quatro letras: ");
        String chave = in.nextLine();
        chave = chave.replaceAll("\\s", "");
        chave = chave.toUpperCase();

        //chave para matriz 2x2
        int[][] chave2D = new  int[2][2];
        int itr3 = 0;
        for (int i =0; i<2; i++){
            for (int j = 0; j<2; j++){
                chave2D[i][j] = (int) chave.charAt(itr3) - 65;
                itr3++;
            }
        }

        //validando achave
        //encontrando determinante da matriz-chave
        int deter = chave2D[0][0] * chave2D [1][1] - chave2D[0][1] * chave2D[1][0];
        deter = moduloFunc(deter, 26);

        //inverso multiplicativo da matriz da chave
        int inversomul = -1;
        for( int i = 0; i< 26; i++){
            int tempInv = deter * i;
            if(moduloFunc(tempInv, 26)==1){
                inversomul = i;
                break;
            }else {
                continue;
            }
        }

        if(inversomul == -1){
            System.out.println("chave invalida");
            System.exit(1);
        }

        String encriptarTexto = "";
        int itrCount= msg.length() / 2;
          if(lenChk == 0){
              //se o tamanho da msg % 2 = 0
              for(int i =0; i<itrCount; i++){
                  int temp1 = msg2D[0][i] * chave2D [0][0] +
                          msg2D[1][i] * chave2D [0][1];
                  encriptarTexto += (char)((temp1%26) + 65);

                  int temp2 = msg2D[0][i] * chave2D [1][0] +
                          msg2D[1][i] * chave2D [1][1];
                  encriptarTexto += (char)((temp2%26) + 65);
              }
          }else {
              //se o tamanho da msg % 2 != 0 (mensagem com tamanho inregular)
              for(int i =0; i<itrCount-1; i++){
                  int temp1 = msg2D[0][i] * chave2D [0][0] + msg2D[1][i] * chave2D [0][1];
                  encriptarTexto += (char)((temp1%26) + 65);

                  int temp2 = msg2D[0][i] * chave2D [1][0] + msg2D[1][i] * chave2D [1][1];
                  encriptarTexto += (char)((temp2%26) + 65);
               }
          }
        System.out.println("texto enciptado: "+ encriptarTexto);
    }


    private static void cipherDescryption(){

        System.out.print("digite a mensagem:");
        String msg = in.nextLine();
        msg = msg.replaceAll("\\s", "");
        msg = msg.toUpperCase();

        //se o comprimento irregular, em seguida, executa o preenchimento
        int lenChk = 0;
        if (msg.length() % 2 != 0){
            msg += "0";
            lenChk = 1;
        }

        //mensagem para matrizes
        int[][] msg2D = new int[2][msg.length()];
        int itr1 = 0, itr2 = 0;
        for (int i = 0; i<msg.length(); i ++){
            if(i%2 == 0){
                msg2D[0][itr1] = ((int)msg.charAt(i)) - 65;
                itr1++;
            }else {
                msg2D[1][itr2] = ((int)msg.charAt(i)) - 65;
                itr2++;
            }
        }

        System.out.print("digite uma chave de quatro letras: ");
        String chave = in.nextLine();
        chave = chave.replaceAll("\\s", "");
        chave = chave.toUpperCase();

        //chave para matriz 2x2
        int[][] chave2D = new  int[2][2];
        int itr3 = 0;
        for (int i =0; i<2; i++){
            for (int j = 0; j<2; j++){
                chave2D[i][j] = (int) chave.charAt(itr3) - 65;
                itr3++;
            }
        }

        //validando achave
        //encontrando determinante da matriz-chave
        int deter = chave2D[0][0] * chave2D [1][1] - chave2D[0][1] * chave2D[1][0];
        deter = moduloFunc(deter, 26);

        //inverso multiplicativo da matriz da chave
        int inversomul = -1;
        for( int i = 0; i< 26; i++){
            int tempInv = deter * i;
            if(moduloFunc(tempInv, 26)==1){
                inversomul = i;
                break;
            }else {
                continue;
            }
        }

        //matriz adjunta
        //troca
        int trocaTemp = chave2D[0][0];
        chave2D[0][0] = chave2D [1][1];
        chave2D[1][1] = trocaTemp;

        //sinais de charing
        chave2D[0][1] *= -1;
        chave2D[1][0] *= -1;

        chave2D[0][1]= moduloFunc(chave2D[0][1], 26);
        chave2D[1][0]= moduloFunc(chave2D[1][0], 26);


        //multiplicação do inverso multiplicativo pela matriz adjunta
        for(int i = 0;i <2;i++){
            for (int j =0; j<2; j++){
                chave2D[i][j] *= inversomul;
            }
        }
        for(int i = 0;i <2; i++){
            for (int j =0; j<2; j++){
                chave2D[i][j] = moduloFunc(chave2D[i][j], 26);
            }
        }

        String desencriptarTexto = "";
        int itrCount= msg.length() / 2;
        if(lenChk == 0){
            //se o tamanho da msg % 2 = 0
            for(int i =0; i<itrCount; i++){
                int temp1 = msg2D[0][i] * chave2D [0][0] + msg2D[1][i] * chave2D [0][1];
                desencriptarTexto += (char)((temp1%26) + 65);

                int temp2 = msg2D[0][i] * chave2D [1][0] + msg2D[1][i] * chave2D [1][1];
                desencriptarTexto += (char)((temp2%26) + 65);
            }
        }else {
            //se o tamanho da msg % 2 != 0 (mensagem com tamanho inregular)
            for(int i =0; i<itrCount-1; i++){
                int temp1 = msg2D[0][i] * chave2D [0][0] + msg2D[1][i] * chave2D [0][1];
                desencriptarTexto += (char)((temp1%26) + 65);

                int temp2 = msg2D[0][i] * chave2D [1][0] + msg2D[1][i] * chave2D [1][1];
                desencriptarTexto += (char)((temp2%26) + 65);
            }
        }
        System.out.println("texto desenciptado: "+ desencriptarTexto);

    }

    //modulo da função
    public static int moduloFunc(int a, int b){
        int result = a % b;
        if(result < 0){
            result += b;
        }
        return result;
    }
}
