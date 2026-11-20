import re
import os

with open("app/build.gradle.kts", "r", encoding="utf-8") as f:
    text = f.read()

vname = re.search(r'fun getVersionName\(\).*?return\s+"([^"]+)"', text, re.DOTALL).group(1)
vcode = re.search(r'fun getVersionCode\(\).*?return\s+(\d+)', text, re.DOTALL).group(1)

env_file = os.getenv("GITHUB_ENV")
if env_file:
    with open(env_file, "a", encoding="utf-8") as f:
        f.write(f"VERSION_NAME={vname}\n")
        f.write(f"VERSION_CODE={vcode}\n")
        f.write(f"TAG_NAME=v{vname}\n")

print(f"VERSION_NAME={vname}")
print(f"VERSION_CODE={vcode}")
print(f"TAG_NAME=v{vname}")
