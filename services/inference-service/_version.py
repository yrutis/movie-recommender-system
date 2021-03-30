from pbr.version import VersionInfo
version = VersionInfo('faucet').semantic_version().release_string()
print(version)