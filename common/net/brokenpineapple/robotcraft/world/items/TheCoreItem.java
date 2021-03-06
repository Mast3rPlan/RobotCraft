/*******************************************************************************
 * Copyright (c) 2012 Emiel Tasseel.
 * All rights reserved. This file is part of RobotCraft.
 * 
 * RobotCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * RobotCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with RobotCraft.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.brokenpineapple.robotcraft.world.items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.StatCollector;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TheCoreItem extends Item {

	public TheCoreItem() {
		super(3702);
		super.setItemName("theCore");
		super.setIconIndex(1);
		super.setTabToDisplayOn(CreativeTabs.tabMaterials);
		super.setMaxStackSize(1);

		LanguageRegistry.addName(this, "The Core");
	}

	@Override
	public String getTextureFile() {
		return "/net/brokenpineapple/robotcraft/sprites/misc.png";
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if (itemStack.getTagCompound() == null)
				itemStack.setTagCompound(new NBTTagCompound());

			NBTTagCompound tags = itemStack.getTagCompound();
			if (!tags.hasKey("owner")) {
				tags.setString("owner", player.username);
				player.sendChatToPlayer("You now own this Core");
			} else {
				((EntityPlayerMP) player).serverForThisPlayer.kickPlayerFromServer("Sup noob?");
			}
		}

		return itemStack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, List lines) {
		if (itemStack.hasTagCompound()) {
			NBTTagCompound tags = itemStack.getTagCompound();
			NBTTagString ownerTag = (NBTTagString) tags.getTag("owner");

			if (ownerTag != null) {
				lines.add("\u00a77" + "Owned by " + ownerTag.data);
			}
		}
	}

}
