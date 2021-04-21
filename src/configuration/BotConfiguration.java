package configuration;

/**
 * 
 * @author gautier
 * class who stock all constant need to bot
 */

public class BotConfiguration {
	
	/**
	 * distance in term of tile if upper then construct a new storage
	 */
	public final static int RANGE_RESSOURCE_STORAGE = 10;
	/**
	 * max worker of bot, if lower he will build another worker
	 */
	public final static int MAX_WORKER = 10;
	
	/**
	 * max worker of bot, if lower he will build another infantry
	 */
	public final static int MAX_INFANTRY = 5;
	/**
	 * max worker of bot, if lower he will build another archer
	 */
	public final static int MAX_ARCHER = 5;
	/**
	 * max worker of bot, if lower he will build another cavalry
	 */
	public final static int MAX_CAVALRY = 5;
	/**
	 * max worker of bot, if lower he will build another special
	 */
	public final static int MAX_SPECIAL = 5;
	/**
	 *	range where building are be building
	 */
	public final static int RANGE_HQ = 5;
	/**
	 * range if a storage is far away of building a tower will be construct close to storage
	 */
	public final static int RANGE_TOWER = 3;
}
