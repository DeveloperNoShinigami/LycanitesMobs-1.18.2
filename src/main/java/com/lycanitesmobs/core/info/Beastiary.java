package com.lycanitesmobs.core.info;

import com.lycanitesmobs.ExtendedPlayer;
import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.network.MessageBeastiary;
import com.lycanitesmobs.core.network.MessageCreatureKnowledge;
import com.lycanitesmobs.core.pets.SummonSet;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Beastiary {
	public ExtendedPlayer extendedPlayer;
	public Map<String, CreatureKnowledge> creatureKnowledgeList = new HashMap<>();
	
    // ==================================================
    //                     Constructor
    // ==================================================
	public Beastiary(ExtendedPlayer extendedPlayer) {
		this.extendedPlayer = extendedPlayer;
	}
	
	
    // ==================================================
    //                     Knowledge
    // ==================================================
	public void newKnowledgeList(Map<String, CreatureKnowledge> newKnowledgeList) {
		this.creatureKnowledgeList = newKnowledgeList;
	}

	public void addToKnowledgeList(CreatureKnowledge newKnowledge) {
		CreatureInfo creatureInfo = CreatureManager.getInstance().getCreature(newKnowledge.creatureName);
		if(creatureInfo == null)
			return;
		if(creatureInfo.dummy)
			return;
		this.creatureKnowledgeList.put(newKnowledge.creatureName, newKnowledge);
	}

	public void sendAddedMessage(CreatureInfo creatureInfo) {
		if(extendedPlayer.player.getEntityWorld().isRemote)
			return;
		String message = I18n.translateToLocal("message.soulgazer.new");
		message = message.replace("%creature%", creatureInfo.getTitle());
		extendedPlayer.player.sendMessage(new TextComponentString(message));
		if(creatureInfo.isSummonable()) {
			String summonMessage = I18n.translateToLocal("message.soulgazer.summonable");
			summonMessage = summonMessage.replace("%creature%", creatureInfo.getTitle());
			extendedPlayer.player.sendMessage(new TextComponentString(summonMessage));
		}
		extendedPlayer.player.addStat(ObjectManager.getStat(creatureInfo.name + ".learn"), 1);
	}
	
	public boolean hasFullKnowledge(String creatureName) {
		if(!this.creatureKnowledgeList.containsKey(creatureName))
			return false;
		if(this.creatureKnowledgeList.get(creatureName).completion < 1)
			return false;
		return true;
	}

	
	/**
	 * Returns how many creatures of the specified group the player has descovered.
	 * @param group Group to check with.
	 * @return True if the player has at least one creature form the specific group.
	 */
	public int getCreaturesDescovered(GroupInfo group) {
		if(this.creatureKnowledgeList.size() == 0) {
			return 0;
		}

		int creaturesDescovered = 0;
		for(Entry<String, CreatureKnowledge> creatureKnowledgeEntry : this.creatureKnowledgeList.entrySet()) {
			if(creatureKnowledgeEntry.getValue() != null) {
				if (creatureKnowledgeEntry.getValue().creatureInfo.group == group) {
					creaturesDescovered++;
				}
			}
		}
		return creaturesDescovered;
	}
	
	
    // ==================================================
    //                     Summoning
    // ==================================================
	public Map<Integer, String> getSummonableList() {
		Map<Integer, String> minionList = new HashMap<>();
		int minionIndex = 0;
		for(String minionName : this.creatureKnowledgeList.keySet()) {
			if(SummonSet.isSummonableCreature(minionName)) {
				minionList.put(minionIndex++, minionName);
			}
		}
		return minionList;
	}
	
	
	// ==================================================
    //                    Network Sync
    // ==================================================
	/** Sends a new Beastiary entry (CreatureKnowledge) to the client. For when it's added server side but needs updated client side. **/
	public void sendNewToClient(CreatureKnowledge newKnowledge) {
		MessageCreatureKnowledge message = new MessageCreatureKnowledge(newKnowledge);
		LycanitesMobs.packetHandler.sendToPlayer(message, (EntityPlayerMP)this.extendedPlayer.getPlayer());
	}
	
	/** Sends the whole Beastiary progress to the client, use sparingly! **/
	public void sendAllToClient() {
		MessageBeastiary message = new MessageBeastiary(this);
		LycanitesMobs.packetHandler.sendToPlayer(message, (EntityPlayerMP)this.extendedPlayer.getPlayer());
	}
	
	
	// ==================================================
    //                        NBT
    // ==================================================
   	// ========== Read ===========
    /** Reads a list of Creature Knowledge from a player's NBTTag. **/
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
    	if(!nbtTagCompound.hasKey("CreatureKnowledge"))
    		return;
    	this.newKnowledgeList(new HashMap<String, CreatureKnowledge>());
    	NBTTagList knowledgeList = nbtTagCompound.getTagList("CreatureKnowledge", 10);
    	for(int i = 0; i < knowledgeList.tagCount(); ++i) {
	    	NBTTagCompound nbtKnowledge = (NBTTagCompound)knowledgeList.getCompoundTagAt(i);
    		if(nbtKnowledge.hasKey("CreatureName") && nbtKnowledge.hasKey("Completion")) {
	    		CreatureKnowledge creatureKnowledge = new CreatureKnowledge(
                        this,
	    				nbtKnowledge.getString("CreatureName"),
	    				nbtKnowledge.getDouble("Completion")
	    			);
	    		this.addToKnowledgeList(creatureKnowledge);
    		}
    	}
    }
    
    // ========== Write ==========
    /** Writes a list of Creature Knowledge to a player's NBTTag. **/
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
    	NBTTagList knowledgeList = new NBTTagList();
		for(Entry<String, CreatureKnowledge> creatureKnowledgeEntry : creatureKnowledgeList.entrySet()) {
			CreatureKnowledge creatureKnowledge = creatureKnowledgeEntry.getValue();
			NBTTagCompound nbtKnowledge = new NBTTagCompound();
			nbtKnowledge.setString("CreatureName", creatureKnowledge.creatureName);
			nbtKnowledge.setDouble("Completion", creatureKnowledge.completion);
			knowledgeList.appendTag(nbtKnowledge);
		}
		nbtTagCompound.setTag("CreatureKnowledge", knowledgeList);
    }
}
