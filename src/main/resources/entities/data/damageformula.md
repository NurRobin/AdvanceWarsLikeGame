This is used to calculate the damage shown when attacking an enemy unit with your own unit.

( ( BD * AH) * (( 100 – TC * DH ) / 100) )
Note that the value inside each parenthesis must be rounded down.

BD = Base damage
::: this is the damage percentage shown when AN, ID, DN, AH and DH are worth 1 while TC is worth 0. It can be obtained by matching units, units being full health and being on terrain providing no terrain stars. The base of our calculation for which you can find the values in our Damage Chart..:::

AH = Attacking unit’s HP
:::A unit has 10 HP when deployed and it isn’t shown, however it will lose some in the course of battle and the HP left is displayed over the unit. Don’t forget that the value AH is a fraction out of the maximum of HP: 10.:::

TC = Terrain cover
:::Over certain terrain, units can acquire defence. The efficiency of the increase in defence is displayed through stars. Each star represents a 10% decrease in damage taken. A 4 star terrain, like an HQ or a Mountain will thus have a TC value of 40. A 3 star terrain 30, etc.:::

DH = Defending unit’s HP
:::However, the efficiency of the terrain cover is directly proportional to the HP of the defending unit. This is calculated in the same way as AH except the HP is taken off the defending unit.:::

For example:
Let’s say we’ve got a 4 HP Tank (which still have primary weapon ammo) attacking a 7 HP recon which is over a City.

IA is 1.0 as that is the inherent firepower.
BD is 85 as the Tank still has ammo. Otherwise it would have been 40 as can be found in the Damage Chart.
AN is 1 as the norm is 1.
ID is 1 as the inherent defence is normally 1.
DN is 1 as the norm is 1 at all times.
AH is 0.4 as the Tank has got 4/10 HP.
TC is 30 as City provide 3 cover star.
DH is 0.7 as the recon has 7 HP left.

1 * 85 rounded down = 85
85 * 1 rounded down = 85
85 * 1 rounded down = 85
85 * 1 rounded down = 85
85 * 0.4 rounded down = 34

Now for the terrain cover defence:
100 – 30 * 0.7 rounded down and divided by 100 = 0.79

34 * 0.79 rounded down 26%
This means that the Tank will display a 26% damage percentage when wanting to fire at a recon in the previous given conditions.