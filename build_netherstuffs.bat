@echo off
cd mcp

rem this removes forestry but allows me to keep it in my sourcecode for future use
rem del reobf\minecraft\bstramke\NetherStuffs\PluginForestry.class

mkdir reobf\minecraft\bstramke\NetherStuffsBase
mkdir reobf\minecraft\bstramke\NetherStuffsBase\bstramke

move reobf\minecraft\bstramke\NetherStuffs reobf\minecraft\bstramke\NetherStuffsBase\bstramke
move reobf\minecraft\bstramke\NetherStuffsNEIPlugin reobf\minecraft\bstramke\NetherStuffsBase\bstramke

rem xcopy reobf\minecraft\buildcraft reobf\minecraft\bstramke\NetherStuffsBase\buildcraft\ /S
rem xcopy reobf\minecraft\forestry reobf\minecraft\bstramke\NetherStuffsBase\forestry\ /S
rem xcopy reobf\minecraft\thaumcraft reobf\minecraft\bstramke\NetherStuffsBase\thaumcraft\ /S
rem xcopy reobf\minecraft\ic2 reobf\minecraft\bstramke\NetherStuffsBase\ic2\ /S

mkdir reobf\minecraft\bstramke\NetherStuffsBase\assets
xcopy src\minecraft\assets\netherstuffs reobf\minecraft\bstramke\NetherStuffsBase\assets\netherstuffs\ /S



copy src\minecraft\bstramke\NetherStuffs\mcmod.info reobf\minecraft\bstramke\NetherStuffsBase\mcmod.info

cd reobf\minecraft\bstramke\NetherStuffsBase
"C:\Program Files\7-Zip\7z.exe" a -tzip mod_NetherStuffs.zip *
cd D:\GitHub\NetherStuffs
copy mcp\reobf\minecraft\bstramke\NetherStuffsBase\mod_NetherStuffs.zip mod_NetherStuffs.jar
pause