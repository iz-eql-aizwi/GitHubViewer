# GitHubViewer

GitHub APIを活用して、リポジトリの検索・詳細表示を行えるAndroidアプリケーションです。  
マルチモジュール構成を採用し、保守性・拡張性を意識した設計を行っています。

<br />

## 📱 スクリーンショット
| ホーム画面 (検索・一覧) | 詳細画面 |
| :---: | :---: |
| <img width="270" height="600" alt="GitHubViewer_HomeScreen" src="https://github.com/user-attachments/assets/d1b494e6-c3ad-411e-b063-9650888544ab" /> | <img width="270" height="600" alt="GitHubViewer_DetailScreen_v1 1 0" src="https://github.com/user-attachments/assets/8e1c5248-e202-4b22-a781-fa25e8e1d8b9" /> |

- **リポジトリ検索**：キーワードによるGitHubリポジトリの検索一覧表示
- **詳細閲覧**：選択したリポジトリの詳細情報の表示
<br />

## 🎬 実機動画
アプリの実際の動作やインタラクションの様子です。

> [実機動画を視聴する](https://youtube.com/shorts/sU6Q8dKxbA8)
<br />

## 🏗 アーキテクチャ・プロジェクト構成

本プロジェクトでは、責務の分離と保守性を高めるため、マルチモジュール構成を採用しています。

- **`:app`**：アプリケーションのエントリーポイント、画面ルーティング等
- **`:data`**：API通信（Retrofit等）、リポジトリの実装、データソース
- **`:domain:model`**：アプリ内で使用するドメインモデル・例外定義
- **`:domain:repository`**：リポジトリのインターフェース定義
- **`:presentation:home`**：ホーム画面（検索・一覧）のUIおよびViewModel
- **`:presentation:detail`**：詳細画面のUIおよびViewModel
<br />

## 🛠 技術スタック

- **言語**：Kotlin
- **UI**：Jetpack Compose / Material Design 3
- **非同期・状態管理**：Coroutines / Flow
- **アーキテクチャ**：MVVM / クリーンアーキテクチャ / マルチモジュール
<br />

## 🚀 アップデート情報
- **v1.0.4** (2026-10-11)
  - アプリの初期リリース
- **v1.1.0** (2026-10-12)
  - マークダウンのレンダリング機能追加による詳細画面のビジュアル向上
