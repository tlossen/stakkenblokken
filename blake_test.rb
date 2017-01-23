(1..64).each do |length|
	result = `b2sum -l #{length * 8} 47e0c.bin`
	puts result if result.start_with?("38094")
end