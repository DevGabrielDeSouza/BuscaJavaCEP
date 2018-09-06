import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		//pesquisa e armazena o arquivo que será verificado
		RandomAccessFile f = new RandomAccessFile("cep_ordenado.dat", "r");
		//cria uma nova estrutura de endereço
		Endereco e = new Endereco();
		
		String cep_pesquisado = "0";
		
		//prepara o scanner para receber informações que o usuário inserir
		Scanner input = new Scanner(System.in);
		while(cep_pesquisado.toUpperCase().compareTo("SAIR") != 0){
			
			//informa o usuário como utilizar a aplicação
			System.out.println("Insira o CEP desejado para obter o endereço completo ou digite SAIR para encerrar a aplicação.");

			//lê a informação inserida pelo usuário
			cep_pesquisado = input.nextLine();
			
			if(cep_pesquisado.toUpperCase().compareTo("SAIR") == 0){
				break;
			}else{
				System.out.println("");
				
				//posição inicial
				long inicio = 0;
				
				//calcula o numero do ultimo registro
				long fim = f.length()/300;
				
				//quantidade de iterações
				int i = 0;
				
				//enquanto não tiver percorrido todas as posições
				while(inicio <= fim){
					//adiciona uma iteração
					i++;
					//calcula o meio do arquivo
					long meio = (inicio+fim)/2;
					
					//pega o conjunto de informações no meio
					f.seek((meio)*300);
					//lê o meio como um endereço
					e.leEndereco(f);
					
					//converte o CEP pesquisado para int
					long cep_entrado = Integer.parseInt(cep_pesquisado);
					//converte o CEP atual para int
					long cep_atual = Integer.parseInt(e.getCep());
					
					//verifica se os ceps são idênticos
					if(e.getCep().equals(cep_pesquisado)) {
						//caso sejam, a operação é encerrada
						break;
					}
					//caso o cep pesquisado seja maior que o atual
					if(cep_entrado > cep_atual) {
						//eliminamos a primeira metade do conjunto pesquisado
						inicio = meio + 1;
					//caso o cep pesquisado seja menor que o atual
					}else if(cep_entrado < cep_atual) {
						//eliminamos a metade final do conjunto pesquisado
						fim = meio - 1;
					}
				}
				
				//imprime a quantidade de iterações que o programa precisou
				System.out.println("Quantidade de iterações: " + i);
				
				//Imprime todas as informações do endereço pesquisado
				System.out.println("Logradouro: " + e.getLogradouro());
				System.out.println("Bairro: " + e.getBairro());
				System.out.println("Cidade: " + e.getCidade());
				System.out.println("Estado: " + e.getEstado());
				System.out.println("Sigla: " + e.getSigla());
				System.out.println("Cep: " + e.getCep());
	
				System.out.println("");
				System.out.println("");
			}
		}
		//fecha o scanner
		input.close();
		//fecha o arquivo
		f.close();
		System.out.println("Aplicação Encerrada!");
	}

}
