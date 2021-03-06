package com.builtbroken.icbm.content.missile.recipes;

import com.builtbroken.icbm.api.ICBM_API;
import com.builtbroken.icbm.api.crafting.IModularMissileItem;
import com.builtbroken.icbm.api.missile.IMissileItem;
import com.builtbroken.icbm.api.modules.IMissile;
import com.builtbroken.icbm.api.modules.IWarhead;
import com.builtbroken.icbm.api.warhead.IWarheadItem;
import com.builtbroken.icbm.content.items.ItemExplosive;
import com.builtbroken.icbm.content.missile.data.missile.MissileSize;
import com.builtbroken.icbm.content.missile.parts.MissileModuleBuilder;
import com.builtbroken.mc.api.modules.IModule;
import com.builtbroken.mc.api.modules.IModuleItem;
import com.builtbroken.mc.framework.recipe.item.RecipeShapelessOre;
import com.builtbroken.mc.prefab.inventory.InventoryUtility;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/6/2015.
 */
public class RecipeMicroMissileWarhead extends RecipeShapelessOre
{
    public RecipeMicroMissileWarhead(Object... recipe)
    {
        super(new ItemStack(ICBM_API.itemMissile, 1, MissileSize.MICRO.ordinal()), recipe);
    }

    @Override
    protected boolean itemMatches(ItemStack target, ItemStack input, boolean strict)
    {
        if (!super.itemMatches(target, input, strict))
        {
            if (target != null && input != null)
            {
                if (target != null && input != null && target.getItem() instanceof IModularMissileItem && input.getItem() instanceof IModularMissileItem)
                {
                    //Warhead in missile input need to match recipe warhead required
                    ItemStack warheadA = ((IModularMissileItem) target.getItem()).getWarhead(target);
                    ItemStack warheadB = ((IModularMissileItem) input.getItem()).getWarhead(input);
                    return InventoryUtility.stacksMatch(warheadA, warheadB);
                }
                else if (target.getItem() instanceof IWarheadItem && input.getItem() instanceof IWarheadItem)
                {
                    ItemStack exA = ((IWarheadItem) target.getItem()).getExplosiveStack(target);
                    ItemStack exB = ((IWarheadItem) input.getItem()).getExplosiveStack(input);
                    return InventoryUtility.stacksMatch(exA, exB)
                            //Fix for firework charge having 4B combinations so can't be mapped correctly
                            || InventoryUtility.itemsMatch(ICBM_API.itemExplosive, exA, exB)
                            && exA.getItemDamage() == exB.getItemDamage()
                            && exA.getItemDamage() == ItemExplosive.ExplosiveItems.FIREWORK.ordinal();
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1)
    {
        ItemStack missileStack = null;
        ItemStack warheadStack = null;
        //Find warhead and missile stacks
        for (int i = 0; i < var1.getSizeInventory(); i++)
        {
            final ItemStack slot = var1.getStackInSlot(i);
            if (slot != null)
            {
                //Find missile stack
                if (slot.getItem() instanceof IModularMissileItem)
                {
                    //Only one missile
                    if (missileStack != null)
                    {
                        return null;
                    }
                    missileStack = slot.copy();
                }
                //Find warhead stack
                else if (slot.getItem() instanceof IModuleItem)
                {
                    IModule module = ((IModuleItem) slot.getItem()).getModule(slot);
                    if (module instanceof IWarhead)
                    {
                        //Only one warhead
                        if (warheadStack != null)
                        {
                            return null;
                        }
                        warheadStack = slot.copy();
                    }
                }
                //Anything else is wrong
                else
                {
                    return null;
                }
            }
        }
        //Check if stacks are valid
        if (missileStack != null && warheadStack != null)
        {
            //Generate objects from stack
            IMissile missile = missileStack.getItem() instanceof IMissileItem ? ((IMissileItem) missileStack.getItem()).toMissile(missileStack) : null;
            IWarhead warhead = MissileModuleBuilder.INSTANCE.buildWarhead(warheadStack);
            //Validate
            if (missile != null && warhead != null && missile.getWarhead() == null)
            {
                //Insert warhead and convert back into stack
                missile.setWarhead(warhead);
                return missile.toStack();
            }
        }
        return null;
    }
}
