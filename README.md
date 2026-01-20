# AutoRune Management

The management platform containing all developer and maintainer tools for the AutoRune ecosystem. This module handles OSRS revision analysis, hook generation, API generation, bytecode injection, and mixin implementations - everything needed to maintain and update the platform after game client revisions.

## Technology Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin 1.3.61 with Java 11 interoperability |
| Build System | Maven (multi-module monorepo) |
| Bytecode Manipulation | ASM 7.3.1 (full suite) |
| Async Framework | Kotlin Coroutines 1.3.3 |
| Code Generation | JavaPoet 1.12.0 |
| Serialization | GSON 2.8.6 |
| Collections | Google Guava 28.2-jre |
| File I/O | Commons IO 2.6 |
| Logging | SLF4J 1.7.25 |

## Architecture

The project is organized as a **multi-module Maven monorepo** with clear separation of concerns:

```
Management/
├── pom.xml                    # Root module configuration
├── injector/                  # Bytecode injection framework
├── api-generator/             # Deobfuscated API generation
├── mixins/                    # Mixin implementations for game features
├── updater/                   # OSRS revision analysis & hooks generation
└── osrs-api/                  # Generated API artifacts (external)
```

## Modules

### 1. Updater Module

**Purpose:** Analyzes new OSRS revisions and generates hook mappings via bytecode pattern matching.

**Key Components:**

- **Updater.kt** - Main orchestration pipeline:
  1. Downloads/deobfuscates new OSRS gamepack
  2. Initializes class repository from deobfuscated JAR
  3. Runs class analyzers to find all known structures
  4. Performs staging analysis for field/method counts
  5. Validates against expected counts
  6. Generates hooks JSON
  7. Creates injected JAR
  8. Generates public API

- **ClassAnalyzer.kt** - Base pattern for analyzing game structures
- **100+ Class Analyzers** covering:
  - Animation: `Frame`, `FrameMap`, `Frames`
  - Audio: `AbstractSound`, `AmbientSound`, `Instrument`, `MidiFileReader`
  - Graphics: `Raster`, `Sprite`
  - Entities: `Actor`, `Npc`, `Player`, `InteractiveObject`
  - Widgets: `Widget`, `WidgetNode`
  - And many more game structures...

- **HookGenerator.kt** - Produces JSON mapping files
- **AnalyzerStaging.kt** - Field/method identification and validation
- **PacketAnalyzerRepository.kt** - Network protocol analysis

**Validation Annotations:**
- `@CorrectFieldCount` - Validates expected field counts
- `@CorrectMethodCount` - Validates expected method counts
- `@DependsOn` - Declares analyzer dependencies

### 2. Injector Module

**Purpose:** Performs bytecode-level injection into the OSRS gamepack.

**Architecture:**

```
injector/
├── ClientInjector.kt          # Main injection orchestrator
├── type/
│   ├── ClassInjector.kt       # Adds API interfaces to classes
│   ├── FieldInjector.kt       # Generates getter/setter bytecode
│   ├── MethodInjector.kt      # Creates method wrappers
│   └── mixin/
│       ├── MixinInjector.kt   # Mixin orchestration
│       ├── MixinFieldInjector.kt
│       └── MixinMethodInjector.kt
├── transform/
│   ├── CallbackTransform.kt   # Client callback injection
│   ├── ReflectionTransform.kt # URLClassLoader for reflection spoofing
│   ├── GameShellTransform.kt  # Client tick loop hooks
│   ├── DeviceTransform.kt     # Input blocking checks
│   └── RasterProviderTransform.kt  # Rendering interception
└── util/
    └── DescriptorUtils.kt     # JVM descriptor utilities
```

**Three-Layer Injection Model:**

**Layer 1: API Interfaces**
- `ClassInjector` adds API interface to each game class
- Example: `client` class implements `io/autorune/osrs/api/Client`

**Layer 2: Field/Method Accessors**
```kotlin
// For each field hook:
fun getFieldName(): ReturnType = instance.obfuscatedFieldName
fun setFieldName(value: ReturnType) { instance.obfuscatedFieldName = value }

// For each method hook:
fun methodName(params): ReturnType = instance.obfuscatedMethodName(params)
```

**Layer 3: Specialized Transforms**
| Transform | Purpose |
|-----------|---------|
| `CallbackTransform` | Injects `ClientInstanceCallbacks` instantiation |
| `ReflectionTransform` | Creates URLClassLoader for proper reflection |
| `DeviceTransform` | Adds `isInputBlocked` guards to event handlers |
| `GameShellTransform` | Hooks into `clientTick()` for frame callbacks |
| `RasterProviderTransform` | Intercepts `drawFull()` for rendering |

### 3. API Generator Module

**Purpose:** Generates clean, deobfuscated Java interfaces from hook definitions using JavaPoet.

**Components:**

- **ApiGenerator.kt** - Orchestrates API generation for all classes
- **ClassGenerator.kt** - Generates interface definitions with inheritance
- **FieldGenerator.kt** - Creates abstract getter/setter method specs
- **MethodGenerator.kt** - Creates abstract method specs from descriptors
- **MixinGenerator.kt** - Extracts mixin methods and adds to API

**Output:**
- Pure Java interfaces in `io.autorune.osrs.api` package
- Abstract methods matching the deobfuscated game API
- Getter/setter patterns for field access

### 4. Mixins Module

**Purpose:** Provides client-side extension methods and additional functionality through horizontal code composition.

**Architecture:**
- Mixins are **abstract classes** extending API interfaces
- Marked with `@InsertionMixin` annotation for insertion-point hooks
- Discovered at runtime from compiled class files via `MixinFetcher`

**Mixin Categories:**

| Category | Mixins | Purpose |
|----------|--------|---------|
| Client | `ClientMixin` | Widget tracking, menu actions, debug |
| Devices | `KeyboardListenerMixin`, `MouseListenerMixin`, `MouseWheelListenerMixin` | Input blocking |
| Entities | `ActorMixin`, `NpcMixin`, `InteractiveObjectMixin` | Coordinate conversion, convex hull |
| Model | `ModelMixin` | Click boundary calculation (Jarvis march) |
| Widget | `WidgetMixin` | Render positioning, tree traversal, visibility |

**Mixin Injection Process:**
1. Located via name matching: `{RefName}Mixin` → `RefName` game class
2. All mixin fields copied with getter/setter methods
3. Methods either:
   - **Insertion methods** (`@InsertionMixin`): Code inserted at method start
   - **New methods**: Added as public methods to class
4. Variable offset adjustments for static/instance conversion

## Hooks Data Format

```json
{
  "class_hooks": [
    {
      "obf_name": "yz",
      "ref_name": "Client",
      "package": ".client",
      "super_name": "Object",
      "interfaces": "Listener",
      "field_hooks": [
        {
          "obf_name": "field123",
          "ref_name": "menuActions",
          "ret_type": "[Ljava/lang/String;",
          "owner": "yz",
          "access": 2
        }
      ],
      "method_hooks": [
        {
          "obf_name": "method456",
          "ref_name": "clientTick",
          "descriptor": "()V",
          "owner": "yz",
          "access": 1
        }
      ]
    }
  ]
}
```

## Maintenance Pipeline

The complete version update cycle:

```
1. OBTAIN NEW REVISION
   └─→ Download fresh OSRS gamepack
   └─→ Deobfuscate using transformer pipeline

2. ANALYZE STRUCTURE
   └─→ ClassRepository initialized from deobfuscated JAR
   └─→ ClassAnalyzers run pattern matching (100+ analyzers)
   └─→ Fields and methods identified and validated

3. GENERATE HOOKS
   └─→ HookGenerator produces JSON mapping file
   └─→ Validation: check field/method counts match expectations

4. GENERATE PUBLIC API
   └─→ ApiGenerator creates clean Java interfaces
   └─→ JavaPoet generates source files
   └─→ Published to Maven repository

5. CREATE INJECTED GAMEPACK
   └─→ ClientInjector applies all bytecode modifications
   └─→ Transforms applied for callbacks, reflection, input blocking
   └─→ Mixin code inserted

6. DEPLOY
   └─→ Injected JAR distributed to platform
   └─→ Public API consumed by client applications
```

## Key Design Principles

- **Separation of Concerns** - Each module has single responsibility
- **Bytecode Manipulation** - ASM framework for low-level JVM class modification
- **Code Generation** - JavaPoet produces type-safe API interfaces
- **Extensibility** - Mixins provide additional functionality without modifying core
- **Validation** - Analyzer annotations validate structural expectations
- **Reproducibility** - Deterministic hooks generation from fixed analyzer definitions

## Building

```bash
mvn clean install
```

## File Organization

```
Management/
├── injector/src/main/kotlin/io/autorune/injector/
│   ├── ClientInjector.kt
│   ├── transform/*.kt
│   ├── type/*.kt
│   └── util/*.kt
├── api-generator/src/main/kotlin/io/autorune/osrs/generator/api/
│   ├── ApiGenerator.kt
│   └── type/*.kt
├── mixins/src/main/kotlin/io/autorune/osrs/mixins/
│   ├── ClientMixin.kt
│   ├── devices/*.kt
│   ├── entity/*.kt
│   ├── model/*.kt
│   └── widget/*.kt
└── updater/src/main/kotlin/io/autorune/updater/
    ├── Updater.kt
    ├── analyzer/classes/implementations/*.kt (100+ files)
    ├── hooks/HookGenerator.kt
    └── jar/InjectedDumper.kt
```

## Dependencies

- `autorune-utilities` - Shared ASM extensions and utilities
- External: RuneStar transformer for initial deobfuscation
