require 'nokogiri'

Dir.chdir('build-report')

new_doc = nil

Dir::glob('lint-results-*.xml') do |item|
  file = File.new(item)

  if new_doc.nil?
    new_doc = Nokogiri.XML(file)
  else
    doc = Nokogiri.XML(file)
    issues = doc.search('issue')
    new_doc.at('issues').add_child(issues)
  end
end

File.open('lint-results.xml', 'w') do |file|
  unless new_doc.nil?
    file.puts(new_doc.to_xml(indent: 4))
  end
end