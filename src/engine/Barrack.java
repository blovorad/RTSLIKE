package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Barrack extends Building{

	private ForUnit infantry;
	
	public Barrack(Position position) {
		super(position);
	}
	
	public void addUnit() {
		
	}

	@Override
	public Unit produce() {
		// TODO Auto-generated method stub
		Unit u = null;
		
		if(this.getTimer() <= 0) {
			u = this.getElementCount().get(0);
			this.getElementCount().remove(0);
			if(this.getElementCount().isEmpty()) {
				this.setIsProducing(false);
			}
		}
		
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
	public void StartProd(int id) {
		// TODO Auto-generated method stub
		Unit u = null;
		if(id == EntityConfiguration.INFANTRY) {
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
		this.setIsProducing(true);
	}
	
}
