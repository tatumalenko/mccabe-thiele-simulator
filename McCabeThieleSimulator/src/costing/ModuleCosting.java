package costing;

import java.util.*;

import process.unitoperation.*;

/** This class aims at calculating the capital cost of 
 *  the distillation equipment based on the module costing
 *  method. All equations and correlation coefficients were
 *  taken from Annex A from Turton et al. (1998)
 *  and from Ulrich (1984).
 */
public class ModuleCosting {
	private static final double CEPCI_2014 = 579.7;
	private static final double CEPCI_1996 = 382;

	// capital purchase cost of individual units
	public double calcCapitalPurchase(Column col) throws CostingException {
		double k1, k2, k3;

		if      (col.getDiameter() <= 0.3) 							   
		{k1 = 3.3392; k2 =-0.5538; k3 = 0.2851;}
		else if (col.getDiameter() <= 0.5 && col.getDiameter() >  0.3) 
		{k1 = 3.4746; k2 = 0.5893; k3 = 0.2053;}
		else if (col.getDiameter() <= 1.0 && col.getDiameter() >  0.5) 
		{k1 = 3.6237; k2 = 0.5262; k3 = 0.2146;}
		else if (col.getDiameter() <= 1.5 && col.getDiameter() >  1.0) 
		{k1 = 3.7559; k2 = 0.6361; k3 = 0.1069;}
		else if (col.getDiameter() <= 2.0 && col.getDiameter() >  1.5) 
		{k1 = 3.9484; k2 = 0.4623; k3 = 0.1717;}
		else if (col.getDiameter() <= 2.5 && col.getDiameter() >  2.0) 
		{k1 = 4.0547; k2 = 0.4620; k3 = 0.1558;}
		else if (col.getDiameter() <= 3.0 && col.getDiameter() >  2.5) 
		{k1 = 4.1110; k2 = 0.6094; k3 = 0.0490;}
		else if (col.getDiameter() <= 4.0 && col.getDiameter() >  3.0) 
		{k1 = 4.3919; k2 = 0.2859; k3 = 0.1842;}
		else {
			throw new CostingException("The column diameter is outside "
					+ "the applicable range."
					+ "\nRange Required:0 m < diameter <= 4 m");}

		return cpCost(new double[]{k1,k2,k3},col.getLength());
	}
	public double calcCapitalPurchase(Tray[] trays) {
		return 235+19.80*trays[0].getDiameter() + 75.07*Math.pow(trays[0].getDiameter(),2);
	}
	// pressure factor of individual units
	private double[] calcFp(DistillationColumn col) throws CostingException {
		double fp0 = 1.0, fp;
		
		if (col.getGaugePressure() < 400 && col.getGaugePressure() >= 3.7)  {
			fp = (0.5146 
			+ 0.6838*Math.pow((Math.log10(col.getGaugePressure())),1) 
			+ 0.2970*Math.pow((Math.log10(col.getGaugePressure())),2) 
			+ 0.0235*Math.pow((Math.log10(col.getGaugePressure())),6)
			+ 0.0020*Math.pow((Math.log10(col.getGaugePressure())),8));}																
		else if (col.getGaugePressure() < 3.7 && col.getGaugePressure() >= -0.5) 
		{fp = 1.0;}
		else if (col.getGaugePressure() < -0.5)                                  
		{fp = 1.25;}
		else {
			throw new CostingException("The column pressure is "
					+ "outside the applicable range."
					+ "\nRange Required: pressure <= 400 psig");} 
		
		return new double[]{fp0,fp};
	}
	// material factor of individual units
	private double[] calcFm(DistillationColumn col) throws CostingException {
		double fm0 = 1.0, fm;
		
		if      (Arrays.asList("carbon steel","cs").contains(col.getMaterial(
				).toLowerCase())) 
		{fm = 1.0;}
		else if (Arrays.asList("stainless steel clad","ss, clad","ss clad").contains(
				col.getMaterial().toLowerCase())) 
		{fm = 2.5;}
		else if (Arrays.asList("stainless steel","ss").contains(col.getMaterial(
				).toLowerCase())) 
		{fm = 4.0;}
		else if (Arrays.asList("nickel clad","nickel alloy, clad","nickel, clad",
				"ni clad","ni, clad").contains(col.getMaterial().toLowerCase()))       	
		{fm = 4.5;}
		else if (Arrays.asList("nickel","ni").contains(col.getMaterial().toLowerCase()))               								
		{fm = 9.8;}
		else if (Arrays.asList("titanium clad","ti clad","ti, clad","Titanium, "
				+ "clad").contains(col.getMaterial().toLowerCase()))    
		{fm = 4.9;}
		else if (Arrays.asList("titanium","ti").contains(col.getMaterial().toLowerCase()))             								
		{fm = 10.6;}
		else {
			throw new CostingException("The selected column material does "
					+ "not have any "
					+ "associated costing coefficients: "
					+ col.getMaterial().toLowerCase());}
	
		return new double[]{fm0,fm};
	}
	// bare module cost of individual units
	public double[] calcBareModule(DistillationColumn col) throws CostingException { 
		// only column
		double cbm0, cbm, b1 = 2.50, b2 = 1.72;
		
		cbm0 = calcCapitalPurchase(col)*(b1 + b2*calcFm(col)[0]*calcFp(col)[0])*(
				CEPCI_2014/CEPCI_1996);;
		cbm  = calcCapitalPurchase(col)*(b1 + b2*calcFm(col)[1]*calcFp(col)[1])*(
				CEPCI_2014/CEPCI_1996);;
		return new double[]{cbm0,cbm}; 
	}
	public double[] calcBareModule(Tray[] trays) throws CostingException { 
		// only trays
		double fbm0 = 1.2, fbm, fq;
		double n = trays.length;
		if		(n >= 1.0  && n < (4.0  + 1.0 )/2.0)  
		{fq = 3.0;}
		else if	(n >= (4.0  + 1.0)/2.0  && n < (7.0  + 4.0 )/2.0)  
		{fq = 2.5;}
		else if	(n >= (7.0  + 4.0)/2.0  && n < (10.0 + 7.0)/2.0)  
		{fq = 2.0;}
		else if	(n >= (10.0 + 7.0)/2.0  && n < 20.0)  
		{fq = 1.5;}
		else if	(n >= 20.0) 							   
		{fq = 1.0;}
		else {throw new CostingException("Error while determining "
				+ "Fq value for tray costing.");}
		
		if 		(trays[0].getMaterial().equalsIgnoreCase("carbon steel")) 	 
		{fbm = 1.2;}
		else if (trays[0].getMaterial().equalsIgnoreCase("ss, clad"))        
		{fbm = 2.0;}
		else if (trays[0].getMaterial().equalsIgnoreCase("nickel alloy"))    
		{fbm = 5.0;}
		else {
			throw new CostingException("The selected tray material does not have "
					+ "any associated costing coefficients: "+trays[0].getMaterial());}

		double cbm0 = calcCapitalPurchase(trays)*n*fbm0*fq*(CEPCI_2014/CEPCI_1996);
		double cbm  = calcCapitalPurchase(trays)*n*fbm *fq*(CEPCI_2014/CEPCI_1996);;
		
		return new double[]{cbm0,cbm};
	}
	// total module costs of individual units
	public double calcTotalModule(DistillationColumn col) throws CostingException {
		return 1.18*calcBareModule(col)[1];
	}
	public double calcTotalModule(Tray[] trays) throws CostingException {
		return 1.18*calcBareModule(trays)[1];
	}
	// grass root costs of individual units
	public double calcGrassRoot(DistillationColumn col) throws CostingException {
		return calcTotalModule(col) + 0.50*calcBareModule(col)[0];
	}
	public double calcGrassRoot(Tray[] trays) throws CostingException {
		return calcTotalModule(trays) + 0.50*calcBareModule(trays)[0];
	}
	// total grass root costs
	public double calcGrassRootTotal(DistillationColumn col) throws CostingException {
		return calcGrassRoot(col) + calcGrassRoot(col.getTrays());
	}
	
	// auxillary method representing function for calculating Cp in log-termed expression
	private double cpCost(double[] kArray,double capParameter) {
		return Math.pow(10, kArray[0] + kArray[1]*Math.log10(capParameter) + 
				kArray[2]*Math.pow(Math.log10(capParameter),2));
	}
}


