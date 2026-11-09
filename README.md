# Knockback Resistance Fix / ノックバック耐性修正

A Minecraft Forge mod (1.20.1) that fixes vanilla Minecraft's flawed knockback resistance mechanics.
バニラMinecraftにおけるノックバック耐性の挙動・不具合を修正するMinecraft Forge 1.20.1用MODです。

---

## English

### Overview
In vanilla Minecraft, knockback resistance halves an entity's horizontal velocity regardless of resistance value, while leaving vertical velocity unaltered in the air. This causes airborne entities (jumping, flying, gliding) with knockback resistance to lose their horizontal momentum instantly and plummet downwards when taking damage.

**Knockback Resistance Fix** completely solves this problem:
1. **Preserves Vector Direction:** Scales the true knockback impulse down proportionally to resistance without altering the impulse vector's direction or arbitrarily stripping horizontal velocity in mid-air.
2. **True Maximum Resistance:** When knockback resistance reaches 100% (or configured threshold), the entity receives zero knockback velocity and suffers **no flinch/stun** (no `hurtTime`, no screen tilt / hurt animation packet, and no motion overwrite packet). This ensures you will never be dragged downwards in mid-air upon taking damage!

### Features
- **Accurate Physics Scaling:** Knockback vector decreases in magnitude based on `(1.0 - resistance)` without directional warping.
- **No Mid-air Downward Drag:** Jumping or flying players with max resistance will maintain smooth trajectory when hit.
- **Zero Flinch/Stun at Max Resistance:** Eliminates camera shake, hurtTime freeze, and velocity sync desyncs when resistance is at 100%.
- **Fully Configurable:** Easily toggle features or change resistance threshold in `config/knockbackresistancefix-common.toml`.

### Installation
1. Install [Minecraft Forge](https://files.minecraftforge.net/) for Minecraft 1.20.1.
2. Place `knockbackresistancefix-1.20.1-1.0.0-alpha.1.jar` into your Minecraft `.minecraft/mods` folder.
3. Launch the game!

### License
This project is licensed under the [GNU General Public License v3.0 (GPL-3.0)](LICENSE).

---

## 日本語

### 概要
バニラのMinecraftでは、ノックバック耐性がどれだけ高くても、ダメージを受けると水平方向の移動速度が強制的に半減（/ 2.0）され、さらに空中では垂直方向の反発が加算されません。その結果、ノックバック耐性を持つプレイヤーやモブが空中（ジャンプ中、エリトラ飛行中、Modによる飛行など）でダメージを受けると、水平慣性が急激に奪われて**「下に向かって叩き落とされる（下にノックバックする）」**という不快な現象が発生していました。

**Knockback Resistance Fix** はこの問題を根本から修正します：
1. **ベクトルの方向維持:** ノックバック耐性に応じてノックバックインパルスベクトルそのものの大きさのみを縮小し、ベクトルの向きを変えません。空中で水平速度だけが奪われて下に落ちる挙動を解消します。
2. **最大耐性時の完全無効化・硬直防止:** ノックバック耐性が最大（デフォルト100% / 1.0）の場合、ノックバックベクトルを一切受けず、**ダメージ時の硬直（hurtTime、カメラの揺れパケット、サーバーからのモーション同期パケット）も一切発生しません**。これにより、空中で攻撃を受けても一切下へノックバックせず、スムーズに動き続けることができます。

### 主な特徴
- **精密な物理ベクトルのスケーリング:** ノックバック耐性に応じてインパルスを `(1.0 - 耐性)` で縮小し、バニラのように下向きに折れ曲がることがありません。
- **空中での下ノックバック防止:** 最大耐性時、ジャンプ中や飛行中に被弾しても下に叩き落とされなくなります。
- **最大耐性時の硬直ゼロ:** 画面の傾き、被弾赤点滅硬直、速度パケットによるガクつきを防止。
- **柔軟なコンフィグ:** `config/knockbackresistancefix-common.toml` で各機能の有効/無効や最大判定閾値を調整可能。

### 導入手順
1. Minecraft 1.20.1用の [Minecraft Forge](https://files.minecraftforge.net/) を導入します。
2. ビルドされた `knockbackresistancefix-1.20.1-1.0.0-alpha.1.jar` を `.minecraft/mods` フォルダに配置します。
3. Minecraftを起動してください。

### ライセンス
本MODは [GNU General Public License v3.0 (GPL-3.0)](LICENSE) のもとで公開されています。
