

public class Main {



	public static void main(String[] args) {
		
		
		while(true) {
			Scraper myScraper = new Scraper();
			
			myScraper.scrapear();
			myScraper.escribirFichero();
			try {	
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
        
        

	}

}
