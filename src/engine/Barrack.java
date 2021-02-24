package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Barrack extends Building{

	private ForUnit infantry;
	
	public Barrack(Position position, ForUnit infantry) {
		super(position);
		this.infantry = infantry;
		this.setProductionId(EntityConfiguration.INFANTRY);
	}
	
	public void addUnit() {
		
	}

	@Override
	public Unit produce() {
		Unit u;
		
		u = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(infantry.getTimeToBuild());
		}
		System.out.println("producing infantry final");
		
		return u;
	}

	@Override
	public void attak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookForTarget(Position position, int range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startProd(int id) {
		// TODO Auto-generated method stub
		Unit u = null;
		if(id == EntityConfiguration.INFANTRY) {
			System.out.println("starting infantry production");
			u = new Unit(); //constructeur de unit pas definie mais sinon on ajoute les attribut depuis forUnit infantry en mode infantry.getArmor ...
		}
		else if(id == EntityConfiguration.ARCHER) {
			u = new Unit(); //constructeur de unit pas definie mais sinon on ajoute les attribut depuis forUnit infantry en mode infantry.getArmor ...
		}
		else if(id == EntityConfiguration.CAVALRY) {
			u = new Unit(); //constructeur de unit pas definie mais sinon on ajoute les attribut depuis forUnit infantry en mode infantry.getArmor ...
		}
		else if(id == EntityConfiguration.SPECIAL_UNIT) {
			u = new Unit(); //constructeur de unit pas definie mais sinon on ajoute les attribut depuis forUnit infantry en mode infantry.getArmor ...
		}
		else if(id == EntityConfiguration.WORKER) {
			u = new Unit(); //constructeur de unit pas definie mais sinon on ajoute les attribut depuis forUnit infantry en mode infantry.getArmor ...
		}
		else {
			System.out.println("Invalid id");
		}
		this.getElementCount().add(u);
		this.setTimer(infantry.getTimeToBuild());
		this.setIsProducing(true);
	}
	
}
