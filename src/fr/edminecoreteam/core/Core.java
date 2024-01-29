package fr.edminecoreteam.core;

import fr.edminecoreteam.core.utils.ChangeHubInfo;
import fr.edminecoreteam.core.utils.FoundLobby;
import fr.edminecoreteam.core.utils.SkullNBT;
import fr.edminecoreteam.core.utils.TitleBuilder;
import fr.edminecoreteam.core.listeners.Instance;
import fr.edminecoreteam.core.listeners.PlayerListeners;
import fr.edminecoreteam.core.scoreboard.JoinScoreboardEvent;
import fr.edminecoreteam.core.scoreboard.LeaveScoreboardEvent;
import fr.edminecoreteam.core.scoreboard.ScoreboardManager;
import fr.edminecoreteam.core.teams.ChatTeam;
import fr.edminecoreteam.core.teams.TabListTeams;
import fr.edminecoreteam.core.admin.AdminCommand;
import fr.edminecoreteam.core.admin.AdminInteractions;
import fr.edminecoreteam.core.admin.AdminMenu;
import fr.edminecoreteam.core.api.MySQL;
import fr.edminecoreteam.core.data.KillerFrappesDataManager;
import fr.edminecoreteam.core.data.KillerKillsDataManager;
import fr.edminecoreteam.core.data.SurvivorFrappesDataManager;
import fr.edminecoreteam.core.data.SurvivorMortsDataManager;
import fr.edminecoreteam.core.data.SurvivorReacteursDataManager;
import fr.edminecoreteam.core.data.SurvivorSauverDataManager;
import fr.edminecoreteam.core.gamemanager.InteractionGames;
import fr.edminecoreteam.core.gamemanager.LoadWorld;
import fr.edminecoreteam.core.gui.ChooseSkin;
import fr.edminecoreteam.core.gui.ChooseWeapon;
import fr.edminecoreteam.core.listeners.EventsListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class Core extends JavaPlugin implements PluginMessageListener 
{
    private static Core instance;
    public int srvNumber;
    public boolean isForceStart = false;
    private HashMap<Player, Integer> playerPage = new HashMap<>();
	private HashMap<Player, String> playerPageType = new HashMap<>();
	
	public int weaponKillerID = 0;
	public String weaponKillerItem = "";
	public String weaponKillerItemName = "";
	
    private KillerKillsDataManager killerKillsDataManager;
    private KillerFrappesDataManager killerFrappesDataManager;
    private SurvivorMortsDataManager survivorMortsDataManager;
    private SurvivorReacteursDataManager survivorReacteursDataManager;
    private SurvivorSauverDataManager survivorSauverDataManager;
    private SurvivorFrappesDataManager survivorFrappesDataManager;
    
    private List<String> survivors;
    private List<String> killer;
    private List<String> inGround;
    private List<String> inPendul;
    private List<String> onTheKiller;
    private List<String> generatorsFinish;
    
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private List<Player> players;
    private List<String> playersName;
    public String getGameWorldName;
    public TitleBuilder title;
    public MySQL database;
    public int timeGenerator1 = 0;
	public int getTimerGenerator1() { return timeGenerator1; }
	public int timeGenerator2 = 0;
	public int getTimerGenerator2() { return timeGenerator2; }
	public int timeGenerator3 = 0;
	public int getTimerGenerator3() { return timeGenerator3; }
	public int timeGenerator4 = 0;
	public int getTimerGenerator4() { return timeGenerator4; }
	public int timeGenerator5 = 0;
	public int getTimerGenerator5() { return timeGenerator5; }
	public int timeGenerator6 = 0;
	public int getTimerGenerator6() { return timeGenerator6; }
	public int timeGenerator7 = 0;
	public int getTimerGenerator7() { return timeGenerator7; }
    public int timerEnd;
    public int timerEnd(int i) { this.timerEnd = i; return i; }
    public int timerGame;
    public int timerGame(int i) { this.timerGame = i; return i; }
    public int timerStart;
    public int timerStart(int i) { this.timerStart = i; return i; }
    private List<String> survivorsEchape;
    
    //Initialisation des votes de cartes
    
    //Initialisation des équipes
    
    
    
    private State state;
    
    public Core() {
    	title = new TitleBuilder();
    	
        players = new ArrayList<Player>();
        playersName = new ArrayList<String>();
        
        survivors = new ArrayList<String>();
        killer = new ArrayList<String>();
        inGround = new ArrayList<String>();
        inPendul = new ArrayList<String>();
        onTheKiller = new ArrayList<String>();
        generatorsFinish = new ArrayList<String>();
        survivorsEchape = new ArrayList<String>();
    }
    
    public void onEnable() {
    	saveDefaultConfig();
    	this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    	
    	MySQLConnect();
        setState(State.WAITING);
        ScoreboardManager();
        registerListener();
        
        getGameWorld();
        ChangeHubInfo srvInfo = new ChangeHubInfo(this.getServer().getServerName());
        srvInfo.setMOTD("WAITING");
        FoundLobby fLobby = new FoundLobby();
        srvNumber = fLobby.getServerPerGroup();
    }
    
    @Override
    public void onDisable() {
    	this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
	    this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    	getScoreboardManager().onDisable();
    	
    }
    
    private void MySQLConnect() {
        Core.instance = this;
        (this.database = new MySQL("jdbc:mysql://", this.getConfig().getString("mysql.host"), this.getConfig().getString("mysql.database"), this.getConfig().getString("mysql.user"), this.getConfig().getString("mysql.password"))).connexion();
        database.creatingTableDeadSurvivor();
    }
    
    private void registerListener() {
        Core.instance = this;
        killerKillsDataManager = new KillerKillsDataManager();
        killerFrappesDataManager = new KillerFrappesDataManager();
        survivorMortsDataManager = new SurvivorMortsDataManager();
        survivorReacteursDataManager = new SurvivorReacteursDataManager();
        survivorSauverDataManager = new SurvivorSauverDataManager();
        survivorFrappesDataManager = new SurvivorFrappesDataManager();
		Bukkit.getPluginManager().registerEvents((Listener)new EventsListener(this), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListeners(this), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new Instance(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new SkullNBT(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new InteractionGames(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new TabListTeams(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ChatTeam(), (Plugin)this);
        
        Bukkit.getPluginManager().registerEvents((Listener)new ChooseSkin(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ChooseWeapon(), (Plugin)this);
        
        Bukkit.getPluginManager().registerEvents((Listener)new AdminMenu(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new AdminInteractions(), (Plugin)this);
        this.getCommand("game").setExecutor((CommandExecutor)new AdminCommand());
    }
    
    private void getGameWorld()
    {
    	String world = LoadWorld.getRandomSubfolderName("gameTemplate/");
    	LoadWorld.createGameWorld(world);
    	getGameWorldName = world;
    }
    
    private void ScoreboardManager() {
    	instance = this;
    	
    	Bukkit.getPluginManager().registerEvents(new JoinScoreboardEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveScoreboardEvent(), this);
    	
    	scheduledExecutorService = Executors.newScheduledThreadPool(16);
    	executorMonoThread = Executors.newScheduledThreadPool(1);
    	scoreboardManager = new ScoreboardManager();
    }
    
    
    
    public void setState(State state) {
        this.state = state;
    }
    
    public boolean isState(State state) {
        return this.state == state;
    }
    
    public List<Player> getPlayers() {
        return this.players;
    }
    
    public List<String> getPlayersName() {
        return this.playersName;
    }
    
    public List<String> getSurvivors() {
        return this.survivors;
    }
    
    public List<String> getKiller() {
        return this.killer;
    }
    
    public List<String> getInGround() {
        return this.inGround;
    }
    
    public List<String> getInPendul() {
        return this.inPendul;
    }
    
    public List<String> getOnTheKiller() {
        return this.onTheKiller;
    }
    
    public List<String> getGeneratorsFinish() {
        return this.generatorsFinish;
    }
    
    public List<String> getSurvivorsEchape() {
        return this.survivorsEchape;
    }
    
    public void addKills(Player player, int amount) {
    	killerKillsDataManager.addKills(player.getUniqueId(), amount);
    }

    public void removeKills(Player player, int amount) {
    	killerKillsDataManager.removeKills(player.getUniqueId(), amount);
    }

    public int getKills(Player player) {
        return killerKillsDataManager.getPlayerData(player.getUniqueId()).getKills();
    }
    
    public void addFrappes(Player player, int amount) {
    	killerFrappesDataManager.addFrappes(player.getUniqueId(), amount);
    }

    public void removeFrappes(Player player, int amount) {
    	killerFrappesDataManager.removeFrappes(player.getUniqueId(), amount);
    }

    public int getFrappes(Player player) {
        return killerFrappesDataManager.getPlayerData(player.getUniqueId()).getFrappes();
    }
    
    public void addMorts(Player player, int amount) {
    	survivorMortsDataManager.addMorts(player.getUniqueId(), amount);
    }

    public void removeMorts(Player player, int amount) {
    	survivorMortsDataManager.removeMorts(player.getUniqueId(), amount);
    }

    public int getMorts(Player player) {
        return survivorMortsDataManager.getPlayerData(player.getUniqueId()).getMorts();
    }
    
    public void addReacteurs(Player player, int amount) {
    	survivorReacteursDataManager.addReacteurs(player.getUniqueId(), amount);
    }

    public void removeReacteurs(Player player, int amount) {
    	survivorReacteursDataManager.removeReacteurs(player.getUniqueId(), amount);
    }

    public int getReacteurs(Player player) {
        return survivorReacteursDataManager.getPlayerData(player.getUniqueId()).getReacteurs();
    }
    
    public void addSauver(Player player, int amount) {
    	survivorSauverDataManager.addSauver(player.getUniqueId(), amount);
    }

    public void removeSauver(Player player, int amount) {
    	survivorSauverDataManager.removeSauver(player.getUniqueId(), amount);
    }

    public int getSauver(Player player) {
        return survivorSauverDataManager.getPlayerData(player.getUniqueId()).getSauver();
    }
    
    public void addSurvivorFrappes(Player player, int amount) {
    	survivorFrappesDataManager.addFrappes(player.getUniqueId(), amount);
    }

    public void removeSurvivorFrappes(Player player) {
    	survivorFrappesDataManager.removeFrappes(player.getUniqueId());
    }

    public int getSurvivorFrappes(Player player) {
        return survivorFrappesDataManager.getPlayerData(player.getUniqueId()).getFrappes();
    }
    
    
    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
    		  throws IOException {
    		    Files.walk(Paths.get(sourceDirectoryLocation))
    		      .forEach(source -> {
    		          Path destination = Paths.get(destinationDirectoryLocation, source.toString()
    		            .substring(sourceDirectoryLocation.length()));
    		          try {
    		              Files.copy(source, destination);
    		          } catch (IOException e) {
    		              e.printStackTrace();
    		          }
    		      });
    		}
    
    @Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) 
	{
	    if (!channel.equals("BungeeCord")) 
	    {
	      return;
	    }
	    ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    String subchannel = in.readUTF();
	    if (subchannel.equals("SomeSubChannel")) 
	    {
	      // Use the code sample in the 'Response' sections below to read
	      // the data.
	    }
	}
    
    public int getPage(Player player) {
        if (playerPage.containsKey(player)) {
            return playerPage.get(player);
        }
        return 0;
    }
	
	public void addPage(Player player, int value) {
		playerPage.put(player, value);
    }

	
    public void removePage(Player player) {
    	playerPage.remove(player);
    }
    
    public String getPageType(Player player) {
        if (playerPageType.containsKey(player)) {
            return playerPageType.get(player);
        }
        return "";
    }
	
	public void addPageType(Player player, String value) {
		playerPageType.put(player, value);
    }

	
    public void removePageType(Player player) {
    	playerPageType.remove(player);
    }
    
    public static Core getInstance() {
        return Core.instance;
    }
    
    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }
    
    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }
    
    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }
    
    public static Plugin getPlugin() {
        return null;
    }
}
