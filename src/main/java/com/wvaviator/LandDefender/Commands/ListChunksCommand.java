package com.wvaviator.LandDefender.Commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wvaviator.LandDefender.LDConfiguration;
import com.wvaviator.LandDefender.Data.PlayerData;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.Reference.UUIDManager;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

public class ListChunksCommand implements ICommand {

	private List aliases;
	public ListChunksCommand() {
		this.aliases = new ArrayList();
		this.aliases.add("listchunks");
		this.aliases.add("listclaims");
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "listchunks";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "/listchunks";
	}

	@Override
	public List getAliases() {
		// TODO Auto-generated method stub
		return this.aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {

		if(!(sender instanceof EntityPlayerMP)) {
			Chat.toChat(sender, Chat.noConsole);
			return;
		}
		
		EntityPlayerMP player = (EntityPlayerMP) sender;
		
		if (args.length == 0) {
		
			try {
			
				PlayerData.listAllOwned(player.getUniqueID().toString(), player);
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
		}
		
		if (args.length == 1 && player.canUseCommand(LDConfiguration.useProtectPerm, "protect")) {
			
			String qPlayer = null;
			try {
				qPlayer = UUIDManager.getStringUUIDFromName(args[0]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (qPlayer == null) {
				Chat.toChat(player, Chat.playerNotFound);
				return;
			}
			
			try {
				PlayerData.listAllOwned(qPlayer, player);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return;
			
		}
		
		getCommandUsage(sender);

	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,
			BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
