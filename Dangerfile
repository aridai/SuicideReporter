android_lint.skip_gradle_task = true
Dir.glob("**/reports/lint-results.xml").each { |report|
  android_lint.report_file = report.to_s
  android_lint.lint(inline_mode: true)
}

checkstyle_format.base_path = Dir.pwd
Dir.glob("**/ktlint/*.xml").each { |report|
  checkstyle_format.report report.to_s
}
