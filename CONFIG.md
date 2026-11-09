# Configuration / コンフィグ設定

## English
The configuration file is located at `config/knockbackresistancefix-common.toml`.

### General Settings
| Option | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `fixVectorDirection` | Boolean | `true` | When true, scales the knockback vector down without altering its direction or pulling downwards in mid-air. |
| `preventStunAtMaxResistance` | Boolean | `true` | When true, prevents damage flinch/stun (hurtTime, hurt animation packet, motion overwrite) when knockback resistance reaches the threshold. |
| `maxResistanceThreshold` | Double | `1.0` | The threshold for maximum knockback resistance (1.0 = 100%). Range: `0.0` - `10.0`. |

---

## 日本語
設定ファイルは `config/knockbackresistancefix-common.toml` に生成されます。

### 一般設定 (General Settings)
| 設定項目 | 型 | デフォルト値 | 説明 |
| :--- | :--- | :--- | :--- |
| `fixVectorDirection` | 真偽値 (Boolean) | `true` | 有効時、ノックバック耐性がある場合にノックバックベクトルの向きを変えずに（空中で下向きにならずに）大きさのみを縮小します。 |
| `preventStunAtMaxResistance` | 真偽値 (Boolean) | `true` | 有効時、ノックバック耐性が閾値以上のエンティティはダメージ時の硬直（赤点滅・カメラ揺れパケット・サーバーからのモーション同期）を受けません。 |
| `maxResistanceThreshold` | 小数 (Double) | `1.0` | ノックバック耐性が最大とみなされる閾値（1.0 = 100%）。設定範囲: `0.0` ～ `10.0`。 |
