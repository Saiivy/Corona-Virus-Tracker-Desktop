package gursimar_hehar_project;

/**
 *
 * @author Gursimar Singh Hehar
 * This class represents how the data should be displayed
 */
public class Data {
    String countryName;
    long totalDeaths;
    long newRecovered;
    long totalRecovered;
    long newConfirmed;
    long newDeaths;
    long totalCases;

    public Data(String countryName, long totalDeaths, long newRecovered, long totalRecovered, long newConfirmed, long newDeaths,long totalCases) {
        this.countryName = countryName;
        this.totalDeaths = totalDeaths;
        this.newRecovered = newRecovered;
        this.totalRecovered = totalRecovered;
        this.newConfirmed = newConfirmed;
        this.newDeaths = newDeaths;
        this.totalCases = totalCases;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(long totalCases) {
        this.totalCases = totalCases;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public long getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(long newRecovered) {
        this.newRecovered = newRecovered;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public long getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(long newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public long getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(long newDeaths) {
        this.newDeaths = newDeaths;
    }
    @Override
    public String toString(){
    return "Country: " +countryName +"\n" +"Confirmed Cases: " +newConfirmed +"\n" 
            +"Total Cases: " +totalCases +"\n" +"Total Deaths: " +totalDeaths +"\n"
            +"New Deaths: " +newDeaths +"\n" +"Total Recovered: " +totalRecovered +"\n"
            +"New Cases: " +newConfirmed;
    }
}
