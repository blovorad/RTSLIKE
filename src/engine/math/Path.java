package engine.math;

import java.util.ArrayList;
import java.util.List;

import configuration.GameConfiguration;
import engine.Position;

/**
 * 
 * @author gautier
 * this class is used to make a A* algorithm, it represent the iteration
 */

public class Path {
	/**
	 * openList of the A* who contain Node who are not finish to analyze
	 */
	private List<Node> closeList;
	/**
	 * closeList of the A* who contain node who are finish to analyze
	 */
	private List<Node> openList;
	
	/**
	 * constructor
	 */
	public Path() {
		openList = new ArrayList<Node>();
		closeList = new ArrayList<Node>();
	}
	
	/**
	 * this method calculate cost of a arrayList of node and return the node who have a cost less than other
	 * @param nodes to analyze
	 * @param finalNode, destination 
	 * @param currentNode, who are currently analyze
	 * @return node who are cheaper where to move
	 */
	public Node generatePath(List<Node> nodes, Node finalNode, Node currentNode) {
		List<Node> removeListOpen = new ArrayList<Node>();
		//System.out.println("Generation Path");
		if(closeList.contains(currentNode) == false) {
			closeList.add(currentNode);
		}
		
		for(Node node : nodes) {
			boolean in = false;
			for(Node openNode : openList){
				if(node.getPosition().equals(openNode.getPosition())) {
					if(node.recalculateF(currentNode, finalNode.getPosition())) {
						closeList.add(node);
						removeListOpen.add(node);
					}
					in = true;
					break;
				}
			}
			openList.removeAll(removeListOpen);
			removeListOpen.clear();
			for(Node closeNode : closeList) {
				if(node.getPosition().equals(closeNode.getPosition())) {
					in = true;
					break;
				}
			}
			if(in == false) {
				//System.out.println("NOEUD CURRENT : " + currentNode);
				//System.out.println("Ajoute noeud dans openList");
				node.calculateCost(currentNode, finalNode.getPosition());
				openList.add(node);
			}
		}
		
		if(openList.isEmpty()) {
			return null;
		}
		
		Node nodeBis = openList.get(0);
		
		for(Node node : openList) {
			if(node.getF() < nodeBis.getF()) {
				nodeBis = node;
			}
		}
		
		openList.remove(nodeBis);
		closeList.add(nodeBis);
		
		if(nodeBis.getPosition().equals(finalNode.getPosition())) {
			finalNode.setParent(nodeBis);
			return finalNode;
		}
				
		return nodeBis;
	}
	
	/**
	 * at the end of A* you have a list start to end, to take the good path we need to reverse it
	 * @param node finalNode where you start to reverse
	 * @return list where you have a good path
	 */
	public List<Position> reversePath(Node node) {
		List<Position> posBis = new ArrayList<Position>();
		List<Position> pos = new ArrayList<Position>();
		Node tmp = node;
		
		while(tmp.getParent() != null) {
			Position p = new Position(tmp.getPosition().getX() * GameConfiguration.TILE_SIZE, tmp.getPosition().getY() * GameConfiguration.TILE_SIZE);
			posBis.add(p);
			tmp = tmp.getParent();
		}
		
		for(int i = posBis.size() - 1; i >= 0; i--) {
			pos.add(posBis.get(i));
		}
		
		return pos;
	}
}
