import edu.duke.*;

public class GeneFinder {
    
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int currIndex = dna.indexOf(stopCodon, startIndex+3);
        while(currIndex != -1){
            if ((currIndex - startIndex) % 3 == 0){
                return currIndex;
            }
                currIndex = dna.indexOf(stopCodon, currIndex+1);
            }
        return -1;
    }
    
    public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG", where);
        if(startIndex==-1){return "";}
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        int minIndex = 0;
        if(taaIndex == -1 || (tagIndex!=1 && tagIndex<taaIndex)) {
            minIndex = tagIndex;}
        else {minIndex = taaIndex;} 
        if(minIndex == -1 || (tagIndex!=1 && tgaIndex < minIndex)){
            minIndex = tgaIndex;}
        if(minIndex == -1) return "";
        String result = dna.substring(startIndex,minIndex+3);
        return result;
    }
    
    public StorageResource getAllGenes(String dna){
        StorageResource sr = new StorageResource();
        int startIndex = 0;
        while(true){
            String gene = findGene(dna,startIndex);
            sr.add(gene);
            if(gene.isEmpty()) break;
            else {startIndex = dna.indexOf(gene,startIndex)+gene.length();}
        }
        return sr;
    }
    
    public double cgRatio(String dna){
        double count = 0.00;
        for(String cg : dna.split("")){
            if (cg.equals("c")||cg.equals("g")) count+=1;}
        return (double) (count/dna.length());
    }
    
    public void processGenes(String dna){
        StorageResource genesSr = getAllGenes(dna);
        
        String greater = "";
        int genCount = 0;
        int genMSixty = 0;
        int gencgCount = 0;
        for(String gen:genesSr.data()){
            System.out.println(gen);
            genCount += 1;
            if(gen.length() > 60) {genMSixty+=1;}
            if(gen.length() > greater.length()){greater = gen;}
            if(cgRatio(gen) > 0.35){gencgCount+=1;}
        }
        
        System.out.println("Total genes: "+genCount);
        System.out.println("Genes > 60 count: "+genMSixty);
        System.out.println("Genes w/ cgRadio > 0.35: "+gencgCount);
        System.out.println("The longest gen: "+greater);
        System.out.println("And its length is: "+greater.length());
    }
    
        
    public void testProcessGenes(){
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        processGenes(dna);
    }
    
    
    public void testFindGene(){
        String dna = "AATGCTAACTAGCTGACTAAT";
        StorageResource sr = new StorageResource();
        int startIndex = 0;
        while(true){
            String gene = findGene(dna,startIndex);
            sr.add(gene);
            if(gene.isEmpty()) break;
            else {startIndex = dna.indexOf(gene,startIndex)+gene.length();}
        }
    }
}
