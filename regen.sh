#!/usr/bin/env bash
# Regenerate both processors through the Fluxtion Maven plugin (hosted compiler; needs a Fluxtion API key
# in ~/.fluxtion/fluxtion.apiKeyFile, which the plugin reads itself):
#   DiamondProcessor        from the Java builder  (builder/DiamondBuilder.java,  profile regen)
#   DiamondSpringProcessor  from the Spring XML    (src/main/spring/diamond.xml, profile regen-spring)
# then replace the generator's own licence header with a neutral one and drop the duplicate source copies.
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"
./mvnw -q -B -Pregen package -DskipTests
./mvnw -q -B -Pregen-spring package -DskipTests
python3 - <<'PY'
import pathlib, re
sources = {"DiamondProcessor.java": "builder.DiamondBuilder",
           "DiamondSpringProcessor.java": "src/main/spring/diamond.xml"}
for name, origin in sources.items():
    neutral = (f"/*\n * Generated source: Fluxtion AOT compiler output for {origin} (DEMO project).\n"
               " * Do not edit by hand; regenerate with ./regen.sh (needs a Fluxtion API key).\n */\n")
    for p in pathlib.Path("src/main/java").rglob(name):
        s = p.read_text()
        s = re.sub(r"\A/\*.*?\*/\n", neutral, s, count=1, flags=re.S)
        s = re.sub(r"\n \*\n \* @author [^\n]*\n", "\n", s)
        p.write_text(s)
PY
find src/main/resources -name 'Diamond*Processor.java' -delete
./mvnw -q -B package
echo "regenerated: src/main/java/com/acme/diamond/generated/{DiamondProcessor,spring/DiamondSpringProcessor}.java"
