package engine.entity.unit;

import java.util.List;

import configuration.EntityConfiguration;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.Map;
import engine.math.Collision;

/**
 * 
 * créate:16/02/2021
 * @author girard
 * @version: 16/03/2021
 *
 */

public class Fighter extends Unit{
	
	
	private int state;
	public Fighter (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int armor, int state, Position position, int id, String description, int hpMax, int faction, Position destination, int sightRange, int maxFrame, GraphicsManager graphicsManager){	
		super(hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, armor, position, id, description, destination, hpMax, faction, sightRange, maxFrame, graphicsManager);
		if(faction == EntityConfiguration.PLAYER_FACTION){
			this.state = EntityConfiguration.PASSIF_STATE;
		}
		else{
			this.state = state;
		}
	}

	
	public void update(List<Unit> units, Fog playerFog, Map map) {
		super.update(units, playerFog, map);
		if(this.state == EntityConfiguration.AGRESSIF_STATE && this.getLst_destinations().isEmpty() == true && this.getTarget() == null && this.getTargetUnit() == null && this.getDestination() == null ){
			if(!units.isEmpty()){
				for(Unit value: units){
					if(Collision.collideVision(value, this) && value.getFaction() != this.getFaction()){
						this.setFinalDestination(value.getPosition());
						this.setCurrentAction(EntityConfiguration.WALK);
						this.setTarget(value);
						this.setTargetUnit(value);
						break;
					}
					
				}
			}
		}
		else if(this.state == EntityConfiguration.DEFENSIF_STATE && this.getTarget() == null && this.getDestination() == null && this.isHit()){
			if(!units.isEmpty()){	
				for(Unit value: units){	
					if(Collision.collideVision(value, this) && value.getFaction() != this.getFaction() && value.getTarget() == this){
						this.setFinalDestination(value.getPosition());
						this.setCurrentAction(EntityConfiguration.WALK);
						this.setTarget(value);
						this.setTargetUnit(value);
						break;
					}
					
				}
			}
		}
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
}
