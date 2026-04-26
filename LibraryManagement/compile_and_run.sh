#!/bin/bash
# ─── Library Management System - Build & Run ─────────────────────────────────

SRC_DIR="src"
OUT_DIR="out"

echo "🔨  Compiling..."
mkdir -p "$OUT_DIR"

javac -d "$OUT_DIR" \
  "$SRC_DIR/library/model/Book.java" \
  "$SRC_DIR/library/model/Student.java" \
  "$SRC_DIR/library/model/IssuedRecord.java" \
  "$SRC_DIR/library/util/DataStore.java" \
  "$SRC_DIR/library/util/ConsoleHelper.java" \
  "$SRC_DIR/library/service/LibraryService.java" \
  "$SRC_DIR/library/Main.java"

if [ $? -ne 0 ]; then
  echo "❌  Compilation failed."
  exit 1
fi

echo "✅  Compiled successfully."
echo "🚀  Running Library Management System..."
echo ""
java -cp "$OUT_DIR" library.Main
