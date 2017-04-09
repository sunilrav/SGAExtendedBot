package com.reztek.modules.GuardianControl;

import java.util.ArrayList;
import java.util.HashMap;

import com.reztek.Guardian;
import com.reztek.SGAExtendedBot;
import com.reztek.base.Command;
import com.reztek.base.ICommandProcessor;
import com.reztek.utils.BotUtils;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GuardianControlCommands extends Command implements ICommandProcessor {

	public GuardianControlCommands(JDA pJDA, SGAExtendedBot pBot) {
		super(pJDA, pBot);
	}

	@Override
	public boolean processCommand(String command, String args, MessageReceivedEvent mre) {
		
		/*String[] splitArg = {""};
		if (args != null) {
			splitArg = args.split(" ");
		}*/
		
		switch (command) {
			case "info-ps":
			case "info-xb":
			case "info":
				if (args == null) {
					sendHelpString(mre, "!info[or !info-ps or !info-xb] PlayerNameHere");
				} else {
					playerInfo(mre.getChannel(), args, Guardian.platformCodeFromCommand(command));
				}
				break;
			case "fireteam-ps":
			case "fireteam-xb":
			case "fireteam":
				if (args == null) {
					sendHelpString(mre, "!fireteam[or !fireteam-ps or !fireteam-xb] PlayerNameHere");
				} else {
					fireteamInfo(mre.getChannel(), args, Guardian.platformCodeFromCommand(command));
				}
				break;
			default: 
				return false;
		}
		
		return true;
	}
	
	protected void fireteamInfo(MessageChannel mc, String playerName, String platform) {
		mc.sendTyping().queue();
		Guardian g = Guardian.guardianFromName(playerName, platform);
		ArrayList<Guardian> gFireteam = new ArrayList<Guardian>();
		if (g != null) {
			ArrayList<HashMap<String,String>> members = g.getTrialsFireteamMembershipId();
			for (HashMap<String, String> hashMap : members) {
				gFireteam.add(Guardian.guardianFromMembershipId(hashMap.get("membershipId"), hashMap.get("name"), hashMap.get("platform")));
			}
			mc.sendMessage("**" + g.getName() + "**'s Current Fireteam \n"
					+ "```md\n" +
					g.getName() + "\n" +
					"[Trials Elo]("+ BotUtils.getPaddingForLen(g.getTrialsELO(), 4) + g.getTrialsELO() +")<RK:"+ BotUtils.getPaddingForLen(g.getTrialsRank(), 6) + g.getTrialsRank() +">\n" +
					"[Weekly K/D]("+ BotUtils.getPaddingForLen(g.getThisWeekTrialsKD(), 4)+ g.getThisWeekTrialsKD() +")<GP: "+ BotUtils.getPaddingForLen(g.getThisWeekTrialsMatches(), 5) + g.getThisWeekTrialsMatches() +">\n" +
					"[Season K/D]("+ BotUtils.getPaddingForLen(g.getThisYearTrialsKD(), 4)+ g.getThisYearTrialsKD() +")<GP: "+ BotUtils.getPaddingForLen(g.getThisYearTrialsMatches(), 5) + g.getThisYearTrialsMatches() +">\n" +
					"[Flawlesses]("+ BotUtils.getPaddingForLen(g.getLighthouseCount(), 4)+ g.getLighthouseCount() +")<WK: "+ BotUtils.getPaddingForLen(g.getThisWeekTrialsFlawless(), 5) + g.getThisWeekTrialsFlawless() +  ">\n" +
					"```").queue();
			for (Guardian gFt : gFireteam) {
						mc.sendMessage("```md\n" +
								gFt.getName() + "\n" +
							"[Trials Elo]("+ BotUtils.getPaddingForLen(gFt.getTrialsELO(), 4) + gFt.getTrialsELO() +")<RK:"+ BotUtils.getPaddingForLen(gFt.getTrialsRank(), 6) + gFt.getTrialsRank() +">\n" +
							"[Weekly K/D]("+ BotUtils.getPaddingForLen(gFt.getThisWeekTrialsKD(), 4)+ gFt.getThisWeekTrialsKD() +")<GP: "+ BotUtils.getPaddingForLen(gFt.getThisWeekTrialsMatches(), 5) + gFt.getThisWeekTrialsMatches() +">\n" +
							"[Season K/D]("+ BotUtils.getPaddingForLen(gFt.getThisYearTrialsKD(), 4)+ gFt.getThisYearTrialsKD() +")<GP: "+ BotUtils.getPaddingForLen(gFt.getThisYearTrialsMatches(), 5) + gFt.getThisYearTrialsMatches() +">\n" +
							"[Flawlesses]("+ BotUtils.getPaddingForLen(gFt.getLighthouseCount(), 4)+ gFt.getLighthouseCount() +")<WK: "+ BotUtils.getPaddingForLen(gFt.getThisWeekTrialsFlawless(), 5) + gFt.getThisWeekTrialsFlawless() +  ">\n" +
						"```").queue();
			}
		} else {
			mc.sendMessage("Hmm... Cant seem to find " + playerName + ", You sure you have the right platform or spelling?").queue();
		}
	}
	
	protected void playerInfo(MessageChannel mc, String playerName, String platform) {
		mc.sendTyping().queue();
		Guardian g = Guardian.guardianFromName(playerName, platform);
		if (g != null) {
			mc.sendMessage("Here's some info about **" + g.getName() + "**. \n```md\n" +
					"[Rumble Elo]("+ g.getRumbleELO() +")<#"+ g.getRumbleRank() +">\n" +
					"[Trials Elo]("+ g.getTrialsELO() +")<#"+ g.getTrialsRank() +">\n" +
					"[Flawlesses]("+ g.getLighthouseCount() +")\n" +
					"```").queue();
		} else {
			mc.sendMessage("Hmm... Cant seem to find " + playerName + ", You sure you have the right platform or spelling?").queue();
		}
	}

}