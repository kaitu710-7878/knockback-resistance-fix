# Changelog / 変更履歴

All notable changes to this project will be documented in this file.
すべての注目すべき変更はこのファイルに記録されます。

The format is based on [Keep a Changelog](https://keepachangelog.com/).

## [1.20.1-1.0.0-alpha.1] - 2026-11-09

### English
#### Added
- Initial release of **Knockback Resistance Fix**.
- Proportional knockback vector scaling: Scales down the true knockback impulse without altering its direction or stripping horizontal momentum in the air.
- Maximum resistance nullification: When knockback resistance reaches the threshold (100% / 1.0 by default), completely prevents knockback velocity and damage flinch/stun (hurtTime, hurt animation packet, and motion reset packets).
- Configurable settings via `config/knockbackresistancefix-common.toml`.

### 日本語
#### 追加 (Added)
- **Knockback Resistance Fix** の初期リリース。
- ノックバックベクトルの比例縮小: 空中で水平速度だけが奪われて下に落ちるバニラの不具合を修正し、ノックバックベクトルの方向を維持したまま大きさのみを耐性に応じて縮小。
- 最大耐性時の硬直・ベクトル完全無効化: ノックバック耐性が最大（デフォルト1.0 = 100%）に達している場合、ノックバックベクトルを一切受けず、ダメージ硬直（hurtTime、カメラ揺れアニメーションパケット、被弾時モーションパケット同期）も発生しないように修正。
- `config/knockbackresistancefix-common.toml` によるコンフィグ設定対応。
