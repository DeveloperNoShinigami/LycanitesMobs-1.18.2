# Porting Lycanites Mobs to Forge 1.20.1

This document tracks initial work on bringing the classic mod up to date with the latest Minecraft Forge release.

## Goals
- Maintain creature behaviour, item mechanics and existing configs.
- Use modern ForgeGradle tooling.
- Replace legacy event hooks and registries with the new Forge 1.20.1 equivalents.

## Suggested new features
- Data-driven spawn rules using the built in Forge datapack loaders.
- Built-in support for the Forge animation system allowing smoother entity rendering.
- Tag based item grouping for improved compatibility with other mods.

## Porting steps
1. Update `build.gradle` to use ForgeGradle `5.1+` and target Minecraft `1.20.1`.
2. Create a new entry point using the `@Mod` annotation that registers blocks, items and entities via `DeferredRegister`.
3. Incrementally migrate each mob package, replacing deprecated methods and verifying model loaders.
4. Rework network code to use `ForgeNetworking`.

This file will expand as the port progresses.

## Progress
- Basic mod entry point and item DeferredRegister created. A sample item `ancient_fruit` now loads in game for verification.
